package com.soa.order.service;

import com.alibaba.fastjson.JSONObject;
import com.soa.order.client.HospitalFeignClient;
import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.*;
import com.soa.order.repository.OrdersRepository;
import com.soa.order.repository.ReservationRepository;
import com.soa.order.utils.PostUtil;
import com.soa.order.views.MsmVo;
import com.soa.order.views.ReservationVo;
import com.soa.order.views.ScheduleMqVo;
import com.soa.order.views.ScheduleVo;
import com.soa.rabbit.constant.MqConst;
import com.soa.rabbit.service.RabbitService;
import com.soa.utils.utils.RandomUtil;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 20:18:21
 */
@Service
public class ReservationService {
    @Autowired
    RabbitService rabbitService;
    
    @Autowired
    PatientFeignClient patientFeignClient;

    @Autowired
    HospitalFeignClient hospitalFeignClient;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    WeixinService weixinService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrdersRepository ordersRepository;

    public boolean haveReserved(String patientId, int scheduleId) {
        Reservation reservation = reservationRepository.findReserved(scheduleId,patientId);
        if(reservation!=null)
            return true;
        return false;
    }

    public List<Reservation> getReservation(String userId) {
        List<Reservation> reservationList=reservationRepository.findUserRes(userId);
        return reservationList;
    }

    public Reservation getReservationById(String reservationId) {
        Optional<Reservation> byId = reservationRepository.findById(reservationId);
        Reservation reservation = byId.orElse(null);
        return reservation;
    }

    @Transactional
    public boolean cancel(String reservationId) {
        Optional<Reservation> byId = reservationRepository.findById(reservationId);
        Reservation reservation = byId.orElse(null);
        if(reservation==null)
            return false;
        reservation.setState(3);//已经取消
        reservationRepository.save(reservation);

        //order如果不是空的，也设置成3
        Orders ordersById = ordersService.getOrdersById(reservationId);
        if(ordersById!=null)
        {
            ordersById.setState(3);//已经取消
            ordersRepository.save(ordersById);
        }

        return true;
    }

    @Transactional
    public boolean cancelPaid(String reservationId) {
        //获取
        boolean b = reservationRepository.existsById(reservationId);
        if(!b)
            return false;
        Optional<Reservation> byId = reservationRepository.findById(reservationId);
        Reservation reservation = byId.orElse(null);
        if(reservation==null)
            return false;
        //状态必须是1（已付款未就诊）才行
        if(reservation.getState()!=1)
            return false;

        // 微信退款，卡退款，根据order的type来判断
        Orders ordersById = ordersService.getOrdersById(reservationId);
        Boolean flag;
        if(ordersById.getType()==0)
            //微信支付
            flag = weixinService.refund(reservationId);
        else
            //卡支付
            flag = ordersService.refundCard(reservationId);

        if(!flag)
            return false;

        // 发送mq更新预约数量+1
        ScheduleMqVo scheduleMqVo=new ScheduleMqVo();
        scheduleMqVo.setId(Integer.parseInt(reservation.getScheduleID()));
        scheduleMqVo.setAddOrSub(1);
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, scheduleMqVo);

        // rabbitmq发短信
        String emailInfo="您好，病人"+reservation.getPatientName()+"成功取消"+reservation.getHospitalName()+
                reservation.getDepartmentName()+"的门诊预约！";
        MsmVo msmVo = new MsmVo(emailInfo,reservation.getUserID());
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM, msmVo);

        //更新医院财务,扣钱
//        ordersService.updateHospFinance(reservation.getHospitalID(),(0-reservation.getCost()));

        // 调医院子系统api取消那边的预约
        cancelHospitalSystemReservation(reservation);

        return true;
    }

    public List<Reservation> getHospResList(String hospitalId) {
        List<Reservation> reservationList=reservationRepository.findHospRes(hospitalId);
        return reservationList;
    }

    public List<Reservation> getDepartResList(String hospitalId, String departId) {
        List<Reservation> hospResList = getHospResList(hospitalId);
        List<Reservation> ans = new ArrayList<>();
        for(Reservation tmp:hospResList){
            if(tmp.getDepartmentID().equals(departId))
                ans.add(tmp);
        }
        return ans;
    }

    public List<Reservation> getScheResList(Integer scheduleId) {
        List<Reservation> reservationList=reservationRepository.findScheRes(scheduleId);
        return reservationList;
    }

    public void cancelHospitalSystemReservation(Reservation reservation){
        // 调医院api取消预约信息


    }

    public boolean queryHaveReserved(String patientId) {
        LocalDate todaysDate = LocalDate.now();
//        System.out.println(todaysDate);
        List<Reservation> reservedList = reservationRepository.findHaveReserved(patientId,todaysDate.toString());
//        for(Reservation tmp:reservedList)
//            System.out.println(tmp);
        if(reservedList.size()!=0)
            return true;
        return false;
    }
}
