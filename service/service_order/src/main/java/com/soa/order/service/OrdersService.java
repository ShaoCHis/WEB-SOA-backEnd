package com.soa.order.service;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.soa.order.model.Orders;
import com.soa.order.model.Reservation;
import com.soa.order.repository.OrdersRepository;
import com.soa.order.repository.ReservationRepository;
import com.soa.order.utils.ConstantPropertiesUtils;
import com.soa.order.utils.HttpClient;
import com.soa.order.utils.PostUtil;
import com.soa.order.views.MsmVo;
import com.soa.rabbit.constant.MqConst;
import com.soa.rabbit.service.RabbitService;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 19:31:47
 */
@Service
public class OrdersService {

    @Autowired
    RabbitService rabbitService;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Transactional
    public void saveOrderInfo(Reservation reservation, int type) {
        //首先查询是否存在相同订单
        Optional<Orders> tmp = ordersRepository.findById(reservation.getID());
        Orders orders1 = tmp.orElse(null);
        if(orders1!=null)
            return;//已存在order
        //添加order
        Orders orders =new Orders();
        orders.setID(reservation.getID());
        orders.setReserveID(reservation.getID());
        orders.setCost(reservation.getCost());
        orders.setType(type);
        orders.setState(0);//待支付
        orders.setTime(new Date());
        ordersRepository.save(orders);
    }

    //成功时改变支付状态
    public void paySuccess(String orderId, Map<String, String> resultMap) {
        Optional<Orders> byId = ordersRepository.findById(orderId);
        Orders orders = byId.orElse(null);
        if(orders==null)
            return;
        //更新order信息
        orders.setState(1);//已支付
        orders.setTime(new Date());
        orders.setTransactionID(resultMap.get("transaction_id"));
        ordersRepository.save(orders);
        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(orders.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        reservation.setState(1);//已支付
        reservationRepository.save(reservation);

        // rabbitmq发短信
        sendEmailByMq(reservation);

        updateHospFinance(reservation.getHospitalID(),orders.getCost());

        // 调医院api添加预约信息
        addHospitalSystemReservation(reservation);

    }

    //根据ordersId获取order
    public Orders getOrdersById(String ordersId){
        Optional<Orders> byId = ordersRepository.findById(ordersId);
        Orders orders = byId.orElse(null);
        return orders;
    }

    //退款时修改reservation和orders的state
    @Transactional
    public void refundOrders(String ordersId,int type){
        //更新order信息
        Orders ordersById = getOrdersById(ordersId);
        if(ordersById==null)
            return;
        ordersById.setTime(new Date());
        ordersById.setState(type);//2为退款中,3为退款成功
        ordersRepository.save(ordersById);
        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(ordersById.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        if(reservation==null)
            return;
        reservation.setState(type);//2为退款中,3为退款成功
        reservationRepository.save(reservation);
    }

    @Transactional
    public boolean cardPay(String orderId, String patientId, Integer type) {
        Optional<Orders> byId = ordersRepository.findById(orderId);
        Orders orders = byId.orElse(null);
        if(orders==null)
            return false;

        //查卡钱够不够
        boolean flag = moneyEnough(patientId, type, orders.getCost());
        if(!flag)
            return false;//钱不够

        //更新order信息
        orders.setState(1);//已支付
        orders.setTime(new Date());
        orders.setTransactionID(patientId);//通过patientId和type来记录卡信息
        ordersRepository.save(orders);

        //扣卡钱
        deductMoney(patientId,type,orders.getCost());

        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(orders.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        reservation.setCardType(type);
        reservation.setCardNum(getPatientCards(patientId,type));
        reservation.setState(1);//已支付
        reservationRepository.save(reservation);

        //更新医院财务
        updateHospFinance(reservation.getHospitalID(),orders.getCost());

        // rabbitmq发短信
        sendEmailByMq(reservation);

        // 调医院api添加预约信息
        addHospitalSystemReservation(reservation);

        return true;
    }

    public boolean moneyEnough(String patientId,int type,int money){
        String url;
        if(type==1)
            //医院就诊卡
            url="http://139.196.194.51:18080/api/patients/"+patientId;
        else if(type==2)
            //社保卡
            url="http://139.196.194.51:18081/api/patients/"+patientId;
        else
            //医保卡
            url="http://139.196.194.51:18081/api/patient2s/"+patientId;

        RestTemplate restTemplate = new RestTemplate();
        Object data = restTemplate.getForEntity(url, JSONObject.class).getBody().get("data");
        if(data==null)
            return false;
        int moneyAns=(int)data;
        if(moneyAns<money)
            return false;//钱不够
        return true;
    }

    public String getPatientCards(String id,int type){
        String[] myUrl =new String[3];
        myUrl[0]="http://139.196.194.51:18080/api/patients/card/"+id;//就诊卡
        myUrl[1]="http://139.196.194.51:18081/api/patients/card/"+id;//社保卡
        myUrl[2]="http://139.196.194.51:18081/api/patient2s/card/"+id;//医保卡

        RestTemplate restTemplate = new RestTemplate();
        Object data = restTemplate.getForEntity(myUrl[type-1], JSONObject.class).getBody().get("data");

        if(data!=null)
            return (String)data;
        return "";
    }

    //加钱，扣钱
    public void deductMoney(String id,int type,int money){
        String[] myUrl =new String[3];
        myUrl[0]="http://139.196.194.51:18080/api/hospitals/updatePatient";//就诊卡
        myUrl[1]="http://139.196.194.51:18081/api/patients/update";//社保卡
        myUrl[2]="http://139.196.194.51:18081/api/patient2s/update";//医保卡

        String location = myUrl[type-1];
        JSONObject postData = new JSONObject();
        postData.put("id", id);
        postData.put("economy", money);
        PostUtil.postUrl(postData,location);
    }

    //更新医院财务
    public void updateHospFinance(String hospitalId,int money){
        String location = "http://139.196.194.51:18080/api/finance";
        JSONObject postData = new JSONObject();
        postData.put("hospitalId", hospitalId);
        postData.put("economy", money);
        PostUtil.postUrl(postData,location);
    }

    //卡退钱，更新状态
    public Boolean refundCard(String id) {
        Orders orders = getOrdersById(id);
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(id);
        Reservation reservation = reservationRepositoryById.orElse(null);
        if(reservation==null)
            return false;
        deductMoney(reservation.getPatientID(),orders.getType(),0-orders.getCost());
        refundOrders(id,3);//退款成功
        return true;
    }

    public void sendEmailByMq(Reservation reservation){
        // 您好，病人xxx成功预约xxx医院xxx科室的门诊，时间为xxx（日期时间），预约序号为第xxx位。
        String emailInfo="您好，病人"+reservation.getPatientName()+"成功预约"+reservation.getHospitalName()+
                reservation.getDepartmentName()+"的门诊，时间为"+reservation.getReserveDate()+" "+
                reservation.getReserveTime()+"，预约序号为第"+reservation.getNumber()+"位。";
        MsmVo msmVo = new MsmVo(emailInfo,reservation.getUserID());
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM, msmVo);
    }

    public void addHospitalSystemReservation(Reservation reservation){
        // 调医院api添加预约信息
        

    }

}