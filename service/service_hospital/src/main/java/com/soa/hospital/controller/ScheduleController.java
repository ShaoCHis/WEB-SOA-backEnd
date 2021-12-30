package com.soa.hospital.controller;

import com.soa.hospital.client.PatientFeignClient;
import com.soa.hospital.model.*;
import com.soa.hospital.service.DepartmentService;
import com.soa.hospital.service.DoctorService;
import com.soa.hospital.service.HospitalService;
import com.soa.hospital.service.ScheduleService;
import com.soa.hospital.views.ReservationVo;
import com.soa.hospital.views.ScheduleInfo;
import com.soa.hospital.views.ScheduleMqVo;
import com.soa.hospital.views.ScheduleVo;
import com.soa.rabbit.constant.MqConst;
import com.soa.rabbit.service.RabbitService;
import com.soa.utils.utils.RandomUtil;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    PatientFeignClient patientFeignClient;

    @Autowired
    RabbitService rabbitService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
            return Result.wrapErrorResult("error");
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

    @ApiOperation(value="根据排班id获取该排班的信息，其他服务调用本函数")
    @GetMapping("inner/getScheduleVo/{scheduleId}")
    public Result<ScheduleVo> getScheduleVo(@PathVariable("scheduleId") int scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        if(schedule==null)
            return Result.wrapErrorResult("error");
        else
        {
            ScheduleVo scheduleVo=new ScheduleVo();
            scheduleVo.setAvailableNumber(schedule.getAvailableNumber());
            scheduleVo.setReservedNumber(schedule.getReservedNumber());
            scheduleVo.setStartTime(schedule.getStartTime());
            scheduleVo.setEndTime(schedule.getEndTime());
            scheduleVo.setId(schedule.getId());
            scheduleVo.setDoctorId(schedule.getDoctor().getId());
            scheduleVo.setDate(schedule.getDate());
            return Result.wrapSuccessfulResult(scheduleVo);
        }

    }

    @ApiOperation(value="先根据service_order服务判断是不是已经预约过了，如果没有预约，再调用本方法" +
            "根据病人id和scheduleId两个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}")
    public Result submitReservation(@PathVariable int scheduleId,
                                    @PathVariable String patientId){
        //排班信息
        Schedule schedule;
        String numberStr = redisTemplate.opsForValue().get("schedule-"+String.valueOf(scheduleId));
        if(!StringUtils.isEmpty(numberStr)) {
            //不是空的
            int number=Integer.parseInt(numberStr);
            if(number>=1){
                redisTemplate.opsForValue().set("schedule-"+String.valueOf(scheduleId),String.valueOf(number-1),10, TimeUnit.SECONDS);
                schedule = scheduleService.getSchedule(scheduleId);
                if(schedule==null)
                    return Result.wrapErrorResult("error");
                //扣库存和发rabbitmq
            }
            else
                //number为0，预约失败
                return Result.wrapErrorResult("error");
        }
        else{
            //扣库存发rabbitmq
            schedule = scheduleService.getSchedule(scheduleId);
            if(schedule==null)
                return Result.wrapErrorResult("error");
            if(schedule.getAvailableNumber()<1)
                return Result.wrapErrorResult("error");
            redisTemplate.opsForValue().set("schedule-"+String.valueOf(scheduleId),String.valueOf(schedule.getAvailableNumber()-1),10, TimeUnit.SECONDS);
        }

        Result patientResult = patientFeignClient.getPatientInfo(patientId);
        //获取病人信息
        if(!patientResult.isSuccess())
            return Result.wrapErrorResult("error");
        Patient patient = (Patient) patientResult.getData();

        //查询医生信息,获取名字和价格,医院id、科室id，医院name、科室name
        Doctor doctor=doctorService.getById(schedule.getDoctor().getId());
        if(doctor==null)
            return Result.wrapErrorResult("error");

        Reservation reservation=new Reservation();
        String randomId = RandomUtil.getFourBitRandom()+RandomUtil.getSixBitRandom();
        reservation.setID(randomId);
        reservation.setUserID(patient.getUserId());
        reservation.setPatientID(patient.getPatientId());
        reservation.setPatientName(patient.getName());
        reservation.setCardType(0);
        reservation.setCardNum("");
        reservation.setDoctorName(doctor.getName());
        reservation.setDoctorTitle(doctor.getTitle());
        reservation.setCost(doctor.getCost());
        reservation.setHospitalID(doctor.getHospital().getId());
        reservation.setHospitalName(doctor.getHospital().getName());
        reservation.setDepartmentID(doctor.getDepartment().getId());
        reservation.setDepartmentName(doctor.getDepartment().getName());
        reservation.setDoctorTitle(doctor.getTitle());
        int num=schedule.getReservedNumber()-schedule.getAvailableNumber()+1;
        reservation.setNumber(num);
        reservation.setReserveDate(schedule.getDate());
        reservation.setReserveTime(schedule.getStartTime());
        reservation.setState(0);//默认未完成
        reservation.setScheduleID(String.valueOf(scheduleId));
        System.out.println(reservation);

        //schedule数量减去1
        schedule.setAvailableNumber(schedule.getAvailableNumber()-1);
        scheduleService.update(schedule);

        // mq保存订单
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_RESERVATION, MqConst.ROUTING_RESERVATION, reservation);

        return Result.wrapSuccessfulResult("success");
    }
}
