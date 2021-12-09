package com.soa.order.service;

import com.soa.order.client.HospitalFeignClient;
import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.*;
import com.soa.order.repository.ReservationRepository;
import com.soa.order.views.ReservationVo;
import com.soa.order.views.ScheduleVo;
import com.soa.rabbit.service.RabbitService;
import com.soa.utils.utils.RandomUtil;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String addReservation(String scheduleId, String patientId,int cardType, String cardId) {
        Result patientResult = patientFeignClient.getPatientInfo(patientId);
        //获取病人信息
        Patient patient;
        if(patientResult.isSuccess())
            patient = (Patient) patientResult.getData();
        else
            patient=new Patient();
        System.out.println(patient);
        List<Card> cards = patient.getCards();
        for(Card card:cards){
            System.out.println(card);
        }
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
        if(cardType==0)
        {
            reservation.setCardType(0);
            reservation.setCardNum("无卡");
        }else{
            //参数传递过来选择的卡
            boolean flag=false;
            List<Card> cards1 = patient.getCards();
            for(Card card:cards1){
                if(card.getCardId().equals(cardId)&&card.getType()==cardType){
                    flag=true;
                    reservation.setCardType(card.getType());//0没有，1xx卡9折，2yy卡8折
                    reservation.setCardNum(card.getCardId());
                }
            }
            if(!flag){
                reservation.setCardType(0);
                reservation.setCardNum("无卡");
            }
        }
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
        //根据卡类型打折
//        if(reservation.getCardType()==1)
//            cost=cost*0.9;
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

        //mq修改schedule可预约数！


//        reservationRepository.save(reservation);
        return reservation.getID();//返回刚刚生成的reservation的id
    }
}
