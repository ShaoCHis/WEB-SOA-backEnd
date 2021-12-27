package com.soa.order.service;

import com.alibaba.fastjson.JSONObject;
import com.soa.order.model.Orders;
import com.soa.order.model.Reservation;
import com.soa.order.repository.OrdersRepository;
import com.soa.order.repository.ReservationRepository;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

        //TODO
        // rabbit发短信：
        // 您好，病人xxx成功预约xxx医院xxx科室的门诊，时间为xxx（日期时间），预约序号为第xxx位。

        //更新医院财务
        String location = "http://139.196.194.51:18080/api/finance";
        JSONObject postData = new JSONObject();
        postData.put("hospitalId", reservation.getHospitalID());
        postData.put("economy", orders.getCost());
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(postData, headers);
        System.out.println(client.postForEntity(location, requestEntity, JSONObject.class).getBody());

        //TODO
        // 调医院api更新预约信息



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
        //更新order信息
        orders.setState(1);//已支付
        orders.setTime(new Date());
        orders.setTransactionID(patientId);//通过patientId和type来记录卡信息
        ordersRepository.save(orders);

        //扣卡钱
        if(type==1)
        {

        }else if(type==2)
        {

        }else{

        }

        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(orders.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        reservation.setCardType(type);

//        reservation.setCardNum();

        reservation.setState(1);//已支付
        reservationRepository.save(reservation);
        //发短信
        //更新医院财务
        //更新医院预约信息

        return false;
    }
}
