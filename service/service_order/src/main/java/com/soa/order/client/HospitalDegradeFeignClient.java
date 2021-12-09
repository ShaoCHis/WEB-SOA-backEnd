package com.soa.order.client;

import com.soa.order.model.Doctor;
import com.soa.order.model.Schedule;
import com.soa.order.views.ReservationVo;
import com.soa.order.views.ScheduleVo;
import com.soa.utils.utils.Result;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 23:24:14
 */
@Component
public class HospitalDegradeFeignClient implements HospitalFeignClient {

    @Override
    public Result<ScheduleVo> getScheduleVo(int scheduleId) {
        return Result.wrapErrorResult("error");
    }

    @Override
    public Result<ReservationVo> getReservationVo(String id) {
        return Result.wrapErrorResult("error");
    }

}
