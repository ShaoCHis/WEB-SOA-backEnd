package com.soa.order.controller;

import com.soa.rabbit.service.RabbitService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description: 预约订单
 * @ author: ShenBo
 * @ date: 2021-12-08 20:17:14
 */
@RestController
@RequestMapping("/order/reservations")
@CrossOrigin
@Api(value="预约下单",tags = "预约下单",description = "预约下单")
public class ReservationController {

    //生成预约订单信息
    //传递病人id和scheduleid

}
