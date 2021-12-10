package com.soa.order.controller;

import com.soa.order.service.ReservationService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ program: demo
 * @ description: 预约订单
 * @ author: ShenBo
 * @ date: 2021-12-08 20:17:14
 */
@RestController
@RequestMapping("/order/reservations")
@CrossOrigin
@Api(value="预约下单",tags = "预约下单",description = "预约下单")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @ApiOperation(value="对于有卡的病人，根据病人id和scheduleId和cardType和cardId四个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}/{cardType}/{cardId}")
    public Result submitReservation(@PathVariable String scheduleId,
                                    @PathVariable String patientId,
                                    @PathVariable int cardType,
                                    @PathVariable String cardId){
        String reservationId = reservationService.addReservation(scheduleId,patientId,cardType,cardId);
        return Result.wrapSuccessfulResult(reservationId);
    }

    @ApiOperation(value="对于没卡的病人，根据病人id和scheduleId两个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}")
    public Result submitReservation(@PathVariable String scheduleId,
                                    @PathVariable String patientId){
        int cardType=0;
        String cardId="";
        String reservationId = reservationService.addReservation(scheduleId,patientId,cardType,cardId);
        return Result.wrapSuccessfulResult(reservationId);
    }

    //要判断一下这个病人是否已经预约了这个scheduleId，不可重复预约！
    //查询用户reservation列表
    //根据reservationId查询详情
    //取消预约

    //支付部分怎么做？

}
