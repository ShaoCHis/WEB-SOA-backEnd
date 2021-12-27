package com.soa.statistic.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-orders",fallback = MoneyDegradeFeignClient.class)
@Component
public interface MoneyFeignClient {

}
