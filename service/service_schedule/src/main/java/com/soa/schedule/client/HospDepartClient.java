package com.soa.schedule.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-hospital")//,fallback =
@Component
public interface HospDepartClient {

}
