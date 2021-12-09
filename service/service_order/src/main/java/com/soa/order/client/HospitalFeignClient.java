package com.soa.order.client;

import com.soa.order.model.Doctor;
import com.soa.order.views.ScheduleVo;
import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-hospital",fallback = HospitalDegradeFeignClient.class)
@Component
public interface HospitalFeignClient {

    //根据scheduleId获取schedule信息
    @GetMapping("/hospital/schedules/inner/getScheduleVo/{scheduleId}")
    public ScheduleVo getScheduleVo(@PathVariable("scheduleId") String scheduleId);

    //根据医生id查询医生信息
    @GetMapping("/hospital/doctors/getDoctorInfo/{id}")
    public Result<Doctor> getDoctorInfo(@PathVariable String id);
}
