package com.soa.user.controller;

import com.soa.user.model.Patient;
import com.soa.user.service.PatientService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 21:48:34
 */
@RestController
@RequestMapping("/user/cards")
@CrossOrigin
@Api(value="卡信息",tags = "卡信息",description = "卡信息")
public class CardController {
    //根据病人id（身份证号）查询他的卡列表
    @Autowired
    PatientService patientService;

    @ApiOperation(value="根据病人id（身份证号）查询他的卡列表" +
            "卡的type：1为医院就诊卡，2为社保卡，3为医保卡")
    @GetMapping("getPatientCards/{id}")
    public Result getPatientCards(@PathVariable String id){
        // TODO
        Patient patient=patientService.getById(id);
        if(patient!=null)
        {

            return Result.wrapSuccessfulResult(0);
        }
        else
            return Result.wrapErrorResult("error");
    }

}