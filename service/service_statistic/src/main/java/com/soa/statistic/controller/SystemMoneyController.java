package com.soa.statistic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 22:06:22
 */
@RestController
@RequestMapping("/statistic/system")
@CrossOrigin
@Api(value="平台流水",tags = "平台流水",description = "平台流水")
public class SystemMoneyController {


}
