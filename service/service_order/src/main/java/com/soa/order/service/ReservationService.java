package com.soa.order.service;

import com.alibaba.fastjson.JSONObject;
import com.soa.order.client.HospitalFeignClient;
import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.*;
import com.soa.order.repository.ReservationRepository;
import com.soa.order.utils.PostUtil;
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

import java.util.ArrayList;
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

    public String addReservation(String scheduleId, String patientId,int cardType, String cardId) {
        Result patientResult = patientFeignClient.getPatientInfo(patientId);
        //获取病人信息
        Patient patient;
        if(patientResult.isSuccess())
            patient = (Patient) patientResult.getData();
        else
            patient=new Patient();

        //获取排班信息
        int scheduleIntId = Integer.parseInt(scheduleId);
        Result schedule = hospitalFeignClient.getScheduleVo(scheduleIntId);
        ScheduleVo scheduleVo;
        if(schedule.isSuccess())
            scheduleVo= (ScheduleVo) schedule.getData();
        else
            scheduleVo=new ScheduleVo();

        Reservation reservation=new Reservation();
        String randomId = RandomUtil.getFourBitRandom()+RandomUtil.getSixBitRandom();
        reservation.setID(randomId);
        reservation.setUserID(patient.getUserId());
        reservation.setPatientID(patient.getPatientId());
        reservation.setPatientName(patient.getName());
        reservation.setCardType(0);
        reservation.setCardNum("");
        System.out.println(scheduleVo);

        //查询医生信息,获取名字和价格,医院id、科室id，医院name、科室name
        Result<ReservationVo> reservationVoResult = hospitalFeignClient.getReservationVo(scheduleVo.getDoctorId());
        ReservationVo reservationVo;
        if(reservationVoResult.isSuccess())
            reservationVo=reservationVoResult.getData();
        else
            reservationVo=new ReservationVo();
        System.out.println(reservationVo);

        reservation.setDoctorName(reservationVo.getDoctorName());
        reservation.setDoctorTitle(reservationVo.getDoctorTitle());
        int cost=reservationVo.getCost();

        reservation.setCost(cost);
        reservation.setHospitalID(reservationVo.getHospitalID());
        reservation.setHospitalName(reservationVo.getHospitalName());
        reservation.setDepartmentID(reservationVo.getDepartmentID());
        reservation.setDepartmentName(reservationVo.getDepartmentName());
        reservation.setDoctorTitle(reservationVo.getDoctorTitle());
        int num=scheduleVo.getReservedNumber()-scheduleVo.getAvailableNumber()+1;
        reservation.setNumber(num);
        reservation.setReserveDate(scheduleVo.getDate());
        reservation.setReserveTime(scheduleVo.getStartTime());
        reservation.setState(0);//默认未完成
        reservation.setScheduleID(scheduleId);
        System.out.println(reservation);

        // mq修改schedule可预约数-1！
        ScheduleMqVo scheduleMqVo=new ScheduleMqVo();
        scheduleMqVo.setId(scheduleIntId);
        scheduleMqVo.setAddOrSub(-1);
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, scheduleMqVo);

        reservationRepository.save(reservation);
        //同时保存订单信息，让用户支付
        return reservation.getID();//返回刚刚生成的reservation的id
    }

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
        boolean b = reservationRepository.existsById(reservationId);
        if(b){
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean cancelPaid(String reservationId) {
        //获取
        boolean b = reservationRepository.existsById(reservationId);
        if(!b)
            return false;
        Optional<Reservation> byId = reservationRepository.findById(reservationId);
        Reservation reservation = byId.orElse(null);
        //状态必须是1（已付款未就诊）才行
        if(reservation.getState()!=1)
            return false;

        //TODO
        // 判断当前时间是否可以取消，schedule的startTime之前可取消，过了不可取消

        // 微信退款，卡退款，根据order的type来判断
        Orders ordersById = ordersService.getOrdersById(reservationId);
        if(ordersById.getType()==0)
        {
            //微信支付
            Boolean flag = weixinService.refund(reservationId);
            if(!flag)
                return false;
        }else{
            //卡支付

        }

        // 发送mq更新预约数量+1
        ScheduleMqVo scheduleMqVo=new ScheduleMqVo();
        scheduleMqVo.setId(Integer.parseInt(reservation.getScheduleID()));
        scheduleMqVo.setAddOrSub(1);
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, scheduleMqVo);

        //TODO
        // 短信提示取消预约成功

        //TODO
        // 调医院子系统api取消那边的预约



        //更新医院财务,扣钱
        String location = "http://139.196.194.51:18080/api/finance";
        JSONObject postData = new JSONObject();
        postData.put("hospitalId", reservation.getHospitalID());
        postData.put("economy", (0-reservation.getCost()));
        PostUtil.postUrl(postData,location);
        reservationRepository.deleteById(reservationId);
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
}
