package com.soa.schedule.controller;

import com.soa.schedule.model.Doctor;
import com.soa.schedule.service.DoctorService;
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
 * @ date: 2021-11-19 22:37:03
 */
@RestController
@RequestMapping("/doctor")
@CrossOrigin
@Api(value="医生信息",tags = "医生信息",description = "医生信息")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @ApiOperation(value="根据医生id获取医生信息")
    @GetMapping("getDoctorInfo/{id}")
    public Result<Doctor> getDoctorInfo(@PathVariable String id){
        Doctor doctor=doctorService.getById(id);
        if(doctor!=null)
            return Result.wrapSuccessfulResult(doctor);
        else
            return Result.wrapErrorResult("error!");
    }



    @ApiOperation(value="根据医院id获取全院医生信息列表")
    @GetMapping("getDoctorList/{id}")
    public Result<List<Doctor>> getHospDocList(@PathVariable String id){
        List<Doctor> hospDocList = doctorService.getHospDocList(id);
        if(hospDocList!=null)
            return Result.wrapSuccessfulResult(hospDocList);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="根据医院id和科室id获取某科室医生信息列表")
    @GetMapping("getDoctorList/{hid}/{did}")
    public Result<List<Doctor>> getDepartDocList(@PathVariable String hid,
                                              @PathVariable String did){
        List<Doctor> departDocList = doctorService.getDepartDocList(hid,did);
        if(departDocList!=null)
            return Result.wrapSuccessfulResult(departDocList);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="添加一个医生")
    @PostMapping("addDoctor")
    public Result addDoctor(@RequestBody Doctor doctor){

        //这里需要调用service_hospital的方法，查询医院id和科室id信息是否合法
        doctor.getHospital();
        doctor.getDepartment();
        //如果不合法，返回错误信息


        //合法则保存,返回正确信息
        doctorService.addDoctor(doctor);
        return Result.wrapSuccessfulResult("success");
    }


}
