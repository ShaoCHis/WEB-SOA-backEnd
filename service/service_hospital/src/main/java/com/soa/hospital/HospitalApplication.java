package com.soa.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @ program: demo
 * @ description: HospitalApplication
 * @ author: ShenBo
 * @ date: 2021-11-16 00:03:23
 */
@SpringBootApplication
@ComponentScan({"com.soa"})
public class HospitalApplication {
    @PostConstruct
    public void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }
}
