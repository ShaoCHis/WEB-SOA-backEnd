package com.soa.user.client;

import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-orders",url="192.168.101.1:8088",fallback = ReservationDegradeFeignClient.class)
@Component
public interface ReservationFeignClient {
    @GetMapping("/orders/reserved/patient/{patientId}")
    public Result haveReserved(@PathVariable("patientId") String patientId);
}
