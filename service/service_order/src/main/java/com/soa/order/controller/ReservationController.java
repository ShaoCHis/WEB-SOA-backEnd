package com.soa.order.controller;

import com.soa.order.client.PatientFeignClient;
import com.soa.order.model.Reservation;
import com.soa.order.model.User;
import com.soa.order.service.ReservationService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="对于有卡的病人，根据病人id和scheduleId和cardType和cardId四个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}/{cardType}/{cardId}")
    public Result submitReservation(@PathVariable String scheduleId,
                                    @PathVariable String patientId,
                                    @PathVariable int cardType,
                                    @PathVariable String cardId){
        //要判断一下这个病人是否已经预约了这个scheduleId，不可重复预约！
        boolean flag=reservationService.haveReserved(patientId,scheduleId);
        if(flag)
            //病人已预约此schedule
            return Result.wrapErrorResult("error");
        String reservationId = reservationService.addReservation(scheduleId,patientId,cardType,cardId);
        return Result.wrapSuccessfulResult(reservationId);
    }

    @ApiOperation(value="对于没卡的病人，根据病人id和scheduleId两个参数生成预约订单信息")
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

    @ApiOperation(value="查询用户reservation列表")
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
}
