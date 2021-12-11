package com.soa.order.controller;

import com.soa.order.service.OrdersService;
import com.soa.order.service.WeixinService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order/weixin")
@CrossOrigin
@Api(value="微信支付",tags = "微信支付",description = "微信支付")
public class WeixinController {

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value="生成微信支付二维码")
    @GetMapping("createNative/{reservationId}")
    public Result createNative(@PathVariable String reservationId) {
        Map map = weixinService.createNative(reservationId);
        return Result.wrapSuccessfulResult(map);
    }

    @ApiOperation(value="查询支付状态")
    @GetMapping("queryPayStatus/{reservationId}")
    public Result queryPayStatus(@PathVariable  String reservationId) {
        //调用微信接口实现支付状态查询
        Map<String, String> resultMap = weixinService.queryPayStatus(reservationId);
        //判断
        if(resultMap == null) {
            return Result.wrapErrorResult("支付出错");
        }
        if("SUCCESS".equals(resultMap.get("trade_state"))) { //支付成功
            //更新订单状态
            String out_trade_no = resultMap.get("out_trade_no");//订单编码
            ordersService.paySuccess(out_trade_no,resultMap);
            return Result.wrapSuccessfulResult("支付成功");
        }
        return Result.wrapSuccessfulResult("支付中");
    }
}
