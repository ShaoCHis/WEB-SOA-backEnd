package com.soa.order.controller;

import com.soa.order.service.ReservationService;
import com.soa.rabbit.service.RabbitService;
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
//        String reservationId = reservationService.addReservation(scheduleId,patientId,cardId);
//        return Result.wrapSuccessfulResult(reservationId);
        return Result.wrapSuccessfulResult("111");
    }


    @ApiOperation(value="对于没卡的病人，根据病人id和scheduleId两个参数生成预约订单信息")
    @PostMapping("submitReservation/{scheduleId}/{patientId}")
    public Result submitReservation(@PathVariable String scheduleId,
                                    @PathVariable String patientId){
//        String reservationId = reservationService.addReservation(scheduleId,patientId,cardId);
//        return Result.wrapSuccessfulResult(reservationId);
        return Result.wrapSuccessfulResult("222");
    }
}