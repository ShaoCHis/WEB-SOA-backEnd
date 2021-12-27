package com.soa.order.controller;

import com.soa.order.client.HospitalFeignClient;
import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.Reservation;
import com.soa.order.model.Schedule;
import com.soa.order.model.User;
import com.soa.order.service.ReservationService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ program: demo
 * @ description: 预约订单
 * @ author: ShenBo
 * @ date: 2021-12-08 20:17:14
 */
@RestController
@RequestMapping("/orders/reservations")
@CrossOrigin
@Api(value="预约下单",tags = "预约下单",description = "预约下单")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    PatientFeignClient patientFeignClient;

    @Autowired
    HospitalFeignClient hospitalFeignClient;

    @ApiOperation(value="根据病人id和scheduleId两个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}")
    public Result submitReservation(@PathVariable String scheduleId,
                                    @PathVariable String patientId){
        int cardType=0;
        String cardId="";
        if(reservationService.haveReserved(patientId,scheduleId))
            return Result.wrapErrorResult("error");
        String reservationId = reservationService.addReservation(scheduleId,patientId,cardType,cardId);
        return Result.wrapSuccessfulResult(reservationId);
    }

    @ApiOperation(value="查询用户reservation列表，reservation的state(状态)：" +
            "0为未付款，1为已付款，2为退款中，3为退款成功")
    @GetMapping("getReservationList/{userId}")
    public Result getReservationList(@PathVariable String userId){
        //判断userId是否正确
        Result<User> userInfo = patientFeignClient.getUserInfo(userId);
        if(!userInfo.isSuccess()){
            //没有查到user
            return Result.wrapErrorResult("error");
        }
        List<Reservation> reservationList = reservationService.getReservation(userId);
        return Result.wrapSuccessfulResult(reservationList);
    }

    @ApiOperation(value="根据reservationId查询预约详情")
    @GetMapping("getReservation/{reservationId}")
    public Result getReservation(@PathVariable String reservationId){
        Reservation reservationById = reservationService.getReservationById(reservationId);
        if(reservationById==null)
            return Result.wrapErrorResult("error");
        else
            return Result.wrapSuccessfulResult(reservationById);
    }

    @ApiOperation(value="根据reservationId取消未付款的预约")
    @DeleteMapping("cancelReservation/{reservationId}")
    public Result cancelReservation(@PathVariable String reservationId){
        boolean flag = reservationService.cancel(reservationId);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="根据reservationId取消已经付款的预约，并退款")
    @GetMapping("cancelPaidReservation/{reservationId}")
    public Result cancelPaidReservation(@PathVariable String reservationId){
        boolean flag = reservationService.cancelPaid(reservationId);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }

    @ApiOperation(value="根据医院id查询reservation列表")
    @GetMapping("getHospResList/{hospitalId}")
    public Result getHospResList(@PathVariable String hospitalId){
        List<Reservation> reservations = reservationService.getHospResList(hospitalId);
        return Result.wrapSuccessfulResult(reservations);
    }

    @ApiOperation(value="根据医院id和科室id查询reservation列表")
    @GetMapping("getDepartResList/{hospitalId}/{departId}")
    public Result getDepartResList(@PathVariable String hospitalId,
                                   @PathVariable String departId){
        List<Reservation> reservations = reservationService.getDepartResList(hospitalId,departId);
        return Result.wrapSuccessfulResult(reservations);
    }

    @ApiOperation(value="根据医生id查询reservation列表,暂不可用")
    @GetMapping("getDoctorResList/{doctorId}")
    public Result getDoctorResList(@PathVariable String doctorId){
        //查询医生的schedule列表
        //查询reservation列表
        Result<List<Schedule>> scheduleResult = hospitalFeignClient.getSchedule(doctorId);
        if(!scheduleResult.isSuccess())
            return Result.wrapErrorResult("error");
        List<Schedule> scheduleList = scheduleResult.getData();
        List<Reservation> ans = new ArrayList<>();
        for(Schedule tmp:scheduleList){
            //对每个scheduleId,查询res列表

        }

        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据scheduleId查询reservation列表，暂不可用")
    @GetMapping("getScheduleResList/{scheduleId}")
    public Result getScheduleResList(@PathVariable String scheduleId){
        List<Reservation> resList = reservationService.getScheResList(scheduleId);
        return Result.wrapSuccessfulResult(resList);
    }

}
