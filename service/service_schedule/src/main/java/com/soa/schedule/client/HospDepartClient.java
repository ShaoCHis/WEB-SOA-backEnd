package com.soa.schedule.client;

import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-hospital",fallback = HospDepartDegradeClient.class)
@Component
public interface HospDepartClient {

    @GetMapping("getHospInfo/{id}")
    Result getHospSet(@PathVariable("id") String id);



}
