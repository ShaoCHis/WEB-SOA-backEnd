package com.soa.order.service;

import com.soa.order.client.HospitalFeignClient;
import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.*;
import com.soa.order.views.ScheduleVo;
import com.soa.rabbit.service.RabbitService;
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

    public String addReservation(String scheduleId, String patientId, String cardId) {
        Result patientResult = patientFeignClient.getPatientInfo(patientId);
        //获取病人信息
        Patient patient;
        if(patientResult.isSuccess())
            patient = (Patient) patientResult.getData();
        else
            patient=new Patient();
        //获取排班信息
        ScheduleVo scheduleVo = hospitalFeignClient.getScheduleVo(scheduleId);

        Reservation reservation=new Reservation();
//        reservation的id自动生成
        reservation.setUserID(patient.getUserId());
        reservation.setPatientID(patient.getPatientId());
        reservation.setPatientName(patient.getName());
        //参数传递过来选择的卡
        boolean flag=false;
        List<Card> cards = patient.getCards();
        for(Card card:cards){
            if(card.getCardId().equals(cardId)){
                flag=true;
                reservation.setCardType(card.getType());//0没有，1xx卡9折，2yy卡8折
                reservation.setCardNum(card.getCardId());
            }
        }
        if(!flag){
            reservation.setCardType(0);
            reservation.setCardNum("无卡");
        }

        String doctorId=scheduleVo.getDoctorId();
        //查询医生信息,获取名字和价格,医院id、科室id，医院name、科室name
        Result<Doctor> doctorInfo = hospitalFeignClient.getDoctorInfo(doctorId);
        Doctor doctor;
        if(doctorInfo.isSuccess())
            doctor=doctorInfo.getData();
        else
            doctor=new Doctor();
        reservation.setDoctorName(doctor.getName());
        reservation.setDoctorTitle(doctor.getTitle());
        int cost=doctor.getCost();
        //根据卡类型打折
        reservation.setCost(cost);
        reservation.setHospitalID(doctor.getHospital().getId());
        reservation.setHospitalName(doctor.getHospital().getName());
        reservation.setDepartmentID(doctor.getDepartment().getId());
        reservation.setDepartmentName(doctor.getDepartment().getName());
        //判断available小于等于0时不可约！
        int num=scheduleVo.getReservedNumber()-scheduleVo.getAvailableNumber()+1;
        reservation.setNumber(num);
        //修改schedule！
        reservation.setReserveDate(scheduleVo.getDate());
        reservation.setReserveTime(scheduleVo.getStartTime());
        reservation.setState(0);//默认未完成
        return reservation.getID();//返回刚刚生成的reservation的id
    }
}
