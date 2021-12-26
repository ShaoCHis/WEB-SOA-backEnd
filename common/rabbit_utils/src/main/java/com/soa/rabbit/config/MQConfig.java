package com.soa.rabbit.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ program: demo
 * @ description: mq消息转换器
 * @ author: ShenBo
 * @ date: 2021-12-07 21:13:55
 */
@Configuration
public class MQConfig {
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}