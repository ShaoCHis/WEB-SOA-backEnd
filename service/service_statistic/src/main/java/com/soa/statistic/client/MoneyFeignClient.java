package com.soa.statistic.client;

import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-orders",fallback = MoneyDegradeFeignClient.class)
@Component
public interface MoneyFeignClient {
    @GetMapping("/orders/statistic/querySystemMoney/{fromDate}/{endDate}")
    public Result querySystemMoney(@PathVariable String fromDate,
                                   @PathVariable String endDate);

    @GetMapping("/orders/statistic/queryHospitalMoney/{fromDate}/{endDate}/{hospitalId}")
    public Result queryHospitalMoney(@PathVariable String fromDate,
                                     @PathVariable String endDate,
                                     @PathVariable String hospitalId);

}
