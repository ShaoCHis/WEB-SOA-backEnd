package com.soa.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @ program: demo
 * @ description: aliyun_oss
 * @ author: ShenBo
 * @ date: 2021-11-16 08:39:24
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

@ComponentScan({"com.soa"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
