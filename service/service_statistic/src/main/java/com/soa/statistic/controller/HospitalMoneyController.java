package com.soa.statistic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 22:04:14
 */
@RestController
@RequestMapping("/statistic/hospital")
@CrossOrigin
@Api(value="医院流水",tags = "医院流水",description = "医院流水")
public class HospitalMoneyController {
    


}
