package com.soa.user.controller;

import com.soa.user.model.Patient;
import com.soa.user.model.User;
import com.soa.user.service.PatientService;
import com.soa.user.service.UserInfoService;
import com.soa.user.views.PatientVo;
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
 * @ date: 2021-11-18 17:42:25
 */
@RestController
@RequestMapping("/user/patients")
@CrossOrigin
@Api(value="病人信息",tags = "病人信息",description = "病人信息")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value="根据病人id获取病人信息")
    @GetMapping("getPatientInfo/{id}")
    public Result<Patient> getPatientInfo(@PathVariable String id){
        Patient patient=patientService.getById(id);
        if(patient!=null)
            return Result.wrapSuccessfulResult(patient);
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="根据用户id获取病人信息列表")
    @GetMapping("getPatListInfo/{id}")
    public Result<List<Patient>> getPatListInfo(@PathVariable String id) {
        User user = userInfoService.getById(id);
        if(user==null)
            return Result.wrapErrorResult("error!");
        else{
            List<Patient> patients = user.getPatients();
            return Result.wrapSuccessfulResult(patients);
        }
    }

    @ApiOperation(value="根据用户id为用户添加病人")
    @PostMapping("addPatient/{userId}")
    public Result addPatient(@PathVariable String userId,
                             @RequestBody PatientVo patientVo){
        //判断用户是否存在
        User user = userInfoService.getById(userId);
        if(user==null)
            return Result.wrapErrorResult("error!");
        boolean flag = patientService.addPatient(userId,patientVo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="根据病人id删除病人")
    @DeleteMapping("deletePatient/{patientId}")
    public Result deletePatient(@PathVariable String patientId){
        boolean flag=patientService.deletePatient(patientId);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }
}