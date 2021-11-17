package com.soa.hospital.controller;

import com.soa.hospital.model.Hospital;
import com.soa.hospital.service.HospInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.soa.utils.utils.Result;

/**
 * @ program: demo
 * @ description: HospInfoController
 * @ author: ShenBo
 * @ date: 2021-11-16 18:32:42
 */
@RestController
@RequestMapping("/hosp")
@CrossOrigin
@Api(value="医院信息",tags = "医院信息",description = "医院信息")
public class HospInfoController {
    @Autowired
    HospInfoService hospInfoService;

    @ApiOperation(value="根据id获取医院信息")
    @GetMapping("getHospInfo/{id}")
    public Result<Hospital> getHospSet(@PathVariable String id) {
        Hospital hospital = hospInfoService.getById(id);
        return Result.wrapSuccessfulResult(hospital);
    }

    @ApiOperation(value="修改医院基本信息")
    @PostMapping("updateHospital")
    public Result updateHospital(@RequestBody Hospital hospital){
        hospInfoService.update(hospital);
        return Result.wrapSuccessfulResult("Success!");
    }

    @ApiOperation(value="按医院id修改医院公告")
    @PostMapping("updateNoticeById")
    public Result updateNoticeById(@RequestBody Hospital hospital){
        hospInfoService.updateNoticeById(hospital);
        return Result.wrapSuccessfulResult("Success!");
    }




}
