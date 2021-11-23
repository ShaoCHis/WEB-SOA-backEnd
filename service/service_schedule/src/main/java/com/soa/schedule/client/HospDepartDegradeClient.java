package com.soa.schedule.client;

import com.soa.utils.utils.Result;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description: 如果因为熔断导致失败，执行这里面的方法
 * @ author: ShenBo
 * @ date: 2021-11-23 10:17:24
 */
@Component
public class HospDepartDegradeClient implements HospDepartClient {

    @Override
    public Result getHospSet(String id) {
        return Result.wrapErrorResult("error");
    }

}
