package com.soa.hospital.receiver;

import com.soa.hospital.service.ScheduleService;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-07 22:18:02
 */
@Component
public class HospitalReceiver {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private RabbitService rabbitService;

    
}
