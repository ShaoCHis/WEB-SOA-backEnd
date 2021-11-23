package com.soa.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @ program: demo
 * @ description: 8086
 * @ author: ShenBo
 * @ date: 2021-11-19 21:49:33
 */
@SpringBootApplication
@EnableFeignClients//用于服务调用的注解，service_edu是消费者，所以这里加这个
@EnableDiscoveryClient//nacos注册的注解
@ComponentScan({"com.soa"})
public class ScheduleApplication {
    @PostConstruct
    public void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }
}
