package com.soa.statistic.client;

import com.soa.utils.utils.Result;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 23:25:45
 */
@Component
public class MoneyDegradeFeignClient implements MoneyFeignClient {

    @Override
    public Result querySystemMoney(String fromDate, String endDate) {
        return Result.wrapErrorResult("error");
    }

    @Override
    public Result queryHospitalMoney(String fromDate, String endDate, String hospitalId) {
        return Result.wrapErrorResult("error");
    }

}
