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

    @ApiOperation(value="根据cardId查询卡余额，这个api还不能用")
    @GetMapping("money/{cardId}")
    public Result GetBalance(@PathVariable String cardId){


        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据reservationId和cardId，使用卡余额付款，这个api还不能用")
    @GetMapping("cardPay/{reservationId}/{cardId}")
    public Result cardPay(@PathVariable String reservationId,
                          @PathVariable String cardId) {


        return Result.wrapSuccessfulResult("success");

        //支付失败是因为卡余额不足，提示前端



    }

}
