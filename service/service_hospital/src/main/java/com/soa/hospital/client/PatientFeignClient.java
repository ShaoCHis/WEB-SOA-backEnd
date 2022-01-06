package com.soa.hospital.client;


import com.soa.hospital.model.Patient;
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
    public Result<Patient> getPatientInfo(@PathVariable("id") String id);

}
