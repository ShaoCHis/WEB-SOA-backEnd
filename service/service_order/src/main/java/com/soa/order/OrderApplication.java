package com.soa.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @ program: demo
 * @ description:订单服务
 * @ author: ShenBo
 * @ date: 2021-11-28 23:34:16
 */
@SpringBootApplication
@ComponentScan({"com.soa"})
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {
    @PostConstruct
    public void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
