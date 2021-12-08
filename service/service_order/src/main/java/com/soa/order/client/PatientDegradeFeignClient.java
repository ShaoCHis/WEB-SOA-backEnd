package com.soa.order.client;

import com.soa.order.model.Patient;
import com.soa.utils.utils.Result;
import org.springframework.stereotype.Component;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 21:47:15
 */
@Component
public class PatientDegradeFeignClient implements PatientFeignClient {

    @Override
    public Result<Patient> getPatientInfo(String id) {
        return Result.wrapErrorResult("获取病人信息失败！");
    }
}
