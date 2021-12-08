package com.soa.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-hospital",fallback = HospitalDegradeFeignClient.class)
@Component
public interface HospitalFeignClient {

    //根据scheduleId获取schedule信息
}
