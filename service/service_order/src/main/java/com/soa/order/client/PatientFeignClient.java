package com.soa.order.client;

import com.soa.order.model.Patient;
import com.soa.order.model.User;
import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-user",fallback = PatientDegradeFeignClient.class)
@Component
public interface PatientFeignClient {

    //根据病人id获取病人信息
    @GetMapping("/user/patients/getPatientInfo/{id}")
    public Result<Patient> getPatientInfo(@PathVariable String id);

    //判断用户id是否正确
    @GetMapping("/user/Info/getUserInfo/{id}")
    public Result<User> getUserInfo(@PathVariable String id);

}
