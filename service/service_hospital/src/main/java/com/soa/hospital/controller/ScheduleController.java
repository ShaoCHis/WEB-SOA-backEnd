package com.soa.hospital.controller;

import com.soa.hospital.service.ScheduleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-24 10:18:50
 */
@RestController
@RequestMapping("/schedule")
@CrossOrigin
@Api(value="排班信息",tags = "排班信息",description = "排班信息")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;


}
