package com.soa.order.service;

import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.Patient;
import com.soa.rabbit.service.RabbitService;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String addReservation(String scheduleId, String patientId) {
        Result patientResult = patientFeignClient.getPatientInfo(patientId);
        //获取病人信息
        Patient patient;
        if(patientResult.isSuccess())
            patient = (Patient) patientResult.getData();
        //获取排班信息


        return "tmp";
    }
}
