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

@FeignClient(name = "service-hospital",fallback = HospitalDegradeFeignClient.class)
@Component
public interface HospitalFeignClient {
    //根据scheduleId获取schedule信息
    @GetMapping("/hospital/schedules/inner/getScheduleVo/{scheduleId}")
    public Result<ScheduleVo> getScheduleVo(@PathVariable("scheduleId") int scheduleId);

    //根据医生id查询医生信息、医院信息、科室信息
    @GetMapping("/hospital/doctors/getReservationVo/{id}")
    public Result<ReservationVo> getReservationVo(@PathVariable String id);

    //根据医生id查询医生schedule列表
    @GetMapping("/hospital/schedules/getSchedule/{doctorId}")
    public Result getSchedule(@PathVariable String doctorId);
}
