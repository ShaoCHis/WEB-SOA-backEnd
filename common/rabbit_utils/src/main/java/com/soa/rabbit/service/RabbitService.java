package com.soa.rabbit.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-07 21:13:55
 */
@Service
public class RabbitService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public boolean sendMessage(String exchange,String routingKey,Object message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
        return true;
    }
}
