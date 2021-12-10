package com.soa.order.service;

import com.soa.order.repository.OrderRepository;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 19:31:47
 */
@Service
public class OrderService {

    @Autowired
    RabbitService rabbitService;

    @Autowired
    OrderRepository orderRepository;


}
