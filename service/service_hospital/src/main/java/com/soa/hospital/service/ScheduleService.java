package com.soa.hospital.service;

import com.soa.hospital.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-24 10:19:49
 */
@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
}
