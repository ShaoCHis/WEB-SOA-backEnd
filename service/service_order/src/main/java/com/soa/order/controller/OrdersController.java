package com.soa.order.controller;

import com.soa.order.service.OrdersService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description: 预约订单的支付订单
 * @ author: ShenBo
 * @ date: 2021-12-08 19:31:09
 */
@RestController
@RequestMapping("/order/orders")
@CrossOrigin
@Api(value="支付订单",tags = "支付订单",description = "支付订单")
public class OrdersController {
    @Autowired
    OrdersService ordersService;


}
