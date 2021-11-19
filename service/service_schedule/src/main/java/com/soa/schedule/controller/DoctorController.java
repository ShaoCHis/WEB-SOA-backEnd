package com.soa.schedule.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-19 22:37:03
 */
@RestController
@RequestMapping("/doctor")
@CrossOrigin
@Api(value="医生信息",tags = "医生信息",description = "医生信息")
public class DoctorController {

}
