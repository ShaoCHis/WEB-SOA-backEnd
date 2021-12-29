package com.soa.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soa.order.model.Reservation;
import com.soa.order.service.OrdersService;
import com.soa.order.service.ReservationService;
import com.soa.order.views.HospitalInfo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-24 21:13:05
 */
@RestController
@RequestMapping("/orders/pay")
@CrossOrigin
@Api(value="卡支付",tags = "卡支付",description = "卡支付")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @Autowired
    ReservationService reservationService;

    @ApiOperation(value="根据patientId和卡的type查询卡上的余额，type:1为就诊卡2为社保卡3为医保卡")
    @GetMapping("money/{patientId}/{type}")
    public Result GetBalance(@PathVariable String patientId,
                             @PathVariable Integer type){
        if(type!=1&&type!=2&&type!=3)
            return Result.wrapErrorResult("error");
        String url;
        if(type==1)
            //医院就诊卡
            url="http://139.196.194.51:18080/api/patients/"+patientId;
        else if(type==2)
            //社保卡
            url="http://139.196.194.51:18081/api/patients/"+patientId;
        else
            //医保卡
            url="http://139.196.194.51:18081/api/patient2s/"+patientId;

        RestTemplate restTemplate = new RestTemplate();
        Object data = restTemplate.getForEntity(url, JSONObject.class).getBody().get("data");
        if(data==null)
            return Result.wrapErrorResult("error");
        int money=(int)data;
        return Result.wrapSuccessfulResult(money);
    }

    @ApiOperation(value="根据reservationId和patientId和卡的type，使用卡余额付款")
    @PostMapping("cardPay/{reservationId}/{patientId}/{type}")
    public Result cardPay(@PathVariable String reservationId,
                          @PathVariable String patientId,
                          @PathVariable Integer type) {
        if(type!=1&&type!=2&&type!=3)
            return Result.wrapErrorResult("error");

        //给reservation记录卡信息
        //给orders表记录支付类型
        //可能余额不足
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null)
            return Result.wrapErrorResult("error");//reservation不应该未空
        ordersService.saveOrderInfo(reservation, type);
        boolean flag = ordersService.cardPay(reservationId, patientId, type);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }


}
