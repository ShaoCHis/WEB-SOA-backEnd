package com.soa.hospital.controller;

import com.soa.hospital.service.ScheduleService;
import com.soa.hospital.views.ScheduleInfo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="为医生添加排班信息")
    @PostMapping("addSchedule/{doctorId}")
    public Result addSchedule(@PathVariable String doctorId,
                              @RequestBody ScheduleInfo scheduleInfo){

        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="查看某医生的排班信息")
    @GetMapping("getSchedule/{doctorId}")
    public Result getSchedule(@PathVariable String doctorId){

        return Result.wrapSuccessfulResult("success");
    }


    //删除排班信息
    //修改排班信息

}
