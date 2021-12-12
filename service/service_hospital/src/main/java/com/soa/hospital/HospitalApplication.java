package com.soa.hospital;

import com.soa.hospital.nettyServer.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
@EnableDiscoveryClient
@EnableJpaAuditing
public class HospitalApplication {
    @PostConstruct
    public void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
        try {
            new NettyServer(12114).start();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
