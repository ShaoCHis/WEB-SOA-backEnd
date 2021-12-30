package com.soa.order.client;

import com.soa.order.model.Doctor;
import com.soa.order.model.Schedule;
import com.soa.order.views.ReservationVo;
import com.soa.order.views.ScheduleVo;
import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-hospital",url="192.168.101.1:8083",fallback = HospitalDegradeFeignClient.class)
@Component
public interface HospitalFeignClient {

    //根据医生id查询医生schedule列表
    @GetMapping("/hospital/schedules/getSchedule/{doctorId}")
    public Result getSchedule(@PathVariable("doctorId") String doctorId);

}
