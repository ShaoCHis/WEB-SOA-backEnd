package com.soa.user.client;

import com.soa.utils.utils.Result;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-29 22:26:46
 */
@Component
public class ReservationDegradeFeignClient implements ReservationFeignClient {

    @Override
    public Result haveReserved(String patientId) {
        return Result.wrapSuccessfulResult("can not delete");
    }

}
