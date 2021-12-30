package com.soa.order.client;

import com.soa.order.model.Patient;
import com.soa.order.model.User;
import com.soa.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-user",url="192.168.101.1:8085",fallback = PatientDegradeFeignClient.class)
@Component
public interface PatientFeignClient {

    //判断用户id是否正确
    @GetMapping("/user/Info/getUserInfo/{id}")
    public Result<User> getUserInfo(@PathVariable("id") String id);

}
