package com.soa.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-18 17:42:25
 */
@RestController
@RequestMapping("/patient")
@CrossOrigin
@Api(value="病人信息",tags = "病人信息",description = "病人信息")
public class PatientController {
}
