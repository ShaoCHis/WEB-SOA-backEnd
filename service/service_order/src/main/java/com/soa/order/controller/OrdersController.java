package com.soa.order.controller;

import com.soa.order.service.OrdersService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="根据cardId和卡的type查询卡余额，这个api还不能用")
    @GetMapping("money/{cardId}/{type}")
    public Result GetBalance(@PathVariable String cardId,
                             @PathVariable Integer type){


        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据reservationId和cardId和卡的type，使用卡余额付款，这个api还不能用")
    @PostMapping("cardPay/{reservationId}/{cardId}/{type}")
    public Result cardPay(@PathVariable String reservationId,
                          @PathVariable String cardId,
                          @PathVariable Integer type) {

        //给reservation记录卡信息
        //给orders表记录支付类型
        // type：1为医院就诊卡，2为社保卡，3为医保卡

        return Result.wrapSuccessfulResult("success");

        //支付失败是因为卡余额不足，提示前端



    }

}
