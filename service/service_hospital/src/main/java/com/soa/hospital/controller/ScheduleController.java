package com.soa.hospital.controller;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Doctor;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.model.Schedule;
import com.soa.hospital.service.DepartmentService;
import com.soa.hospital.service.DoctorService;
import com.soa.hospital.service.HospitalService;
import com.soa.hospital.service.ScheduleService;
import com.soa.hospital.views.ScheduleInfo;
import com.soa.hospital.views.ScheduleVo;
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
 * @ date: 2021-11-24 10:18:50
 */
@RestController
@RequestMapping("/hospital/schedules")
@CrossOrigin
@Api(value="排班信息",tags = "排班信息",description = "排班信息")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value="为医生添加排班信息")
    @PostMapping("addSchedule/{doctorId}")
    public Result addSchedule(@PathVariable String doctorId,
                              @RequestBody ScheduleInfo scheduleInfo){
        boolean flag = scheduleService.addSchedule(doctorId,scheduleInfo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="查看某医生的排班信息")
    @GetMapping("getSchedule/{doctorId}")
    public Result getSchedule(@PathVariable String doctorId){
        Doctor byId = doctorService.getById(doctorId);
        if(byId==null)
            //医生id不正确
            return Result.wrapSuccessfulResult("error");
        List<Schedule> list = scheduleService.getScheduleList(doctorId);
        return Result.wrapSuccessfulResult(list);
    }

    @ApiOperation(value="删除排班信息,scheduleId为int类型")
    @DeleteMapping("deleteSchedule/{scheduleId}")
    public Result deleteSchedule(@PathVariable Integer scheduleId){
        boolean flag=scheduleService.deleteSchedule(scheduleId);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="修改排班信息")
    @PostMapping("updateSchedule")
    public Result updateSchedule(@RequestBody ScheduleVo scheduleVo){
        boolean flag=scheduleService.updateSchedule(scheduleVo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="查看某医院的全部的排班信息列表")
    @GetMapping("getHospSchedule/{hospitalId}")
    public Result getHospSchedule(@PathVariable String hospitalId){
        //检查医院id是否正确
        Hospital byId = hospitalService.getById(hospitalId);
        if(byId==null)
            return Result.wrapErrorResult("error");
        List<Schedule> list = scheduleService.getHospSchedule(hospitalId);
        return Result.wrapSuccessfulResult(list);
    }

    @ApiOperation(value="查看某医院的某科室的排班信息列表")
    @GetMapping("getHospDepartSchedule/{hospitalId}/{departmentId}")
    public Result getHospDepartSchedule(@PathVariable String hospitalId,
                                        @PathVariable String departmentId){
        //医院id是否正确,且医院是否有该科室
        Hospital byId = hospitalService.getById(hospitalId);
        if(byId==null)
            return Result.wrapErrorResult("error");
        List<Department> listById = departmentService.getListById(hospitalId);
        for(Department department:listById){
            if(department.getId().equals(departmentId)){
                List<Schedule> list = scheduleService.getHospDepartSchedule(hospitalId,departmentId);
                return Result.wrapSuccessfulResult(list);
            }
        }
        //没有该科室
        return Result.wrapErrorResult("error");
    }

    //根据排班id获取该排班的信息，其他服务调用本函数
    @GetMapping("inner/getScheduleVo/{scheduleId}")
    public ScheduleVo getScheduleVo(@PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleVo(scheduleId);
    }
}
