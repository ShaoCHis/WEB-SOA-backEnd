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
@RequestMapping("/orders/weixin")
@CrossOrigin
@Api(value="微信支付",tags = "微信支付",description = "微信支付")
public class WeixinController {

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value="点击付款，本函数生成微信支付二维码，" +
            "前端展示二维码（需要安装组件才能展示）给用户扫描支付")
    @GetMapping("createNative/{reservationId}")
    public Result createNative(@PathVariable String reservationId) {
        Map map = weixinService.createNative(reservationId);
        return Result.wrapSuccessfulResult(map);
    }

    @ApiOperation(value="查询支付状态,前端每隔三秒调用一次本方法，收到waiting继续调用；" +
            "收到success停止调用，代表支付成功；收到error停止调用，代表出错")
    @GetMapping("queryPayStatus/{reservationId}")
    public Result queryPayStatus(@PathVariable String reservationId) {
        //调用微信接口实现支付状态查询
        Map<String, String> resultMap = weixinService.queryPayStatus(reservationId);
        //判断
        if(resultMap == null)
            return Result.wrapErrorResult("error");//出错
        if("SUCCESS".equals(resultMap.get("trade_state"))) { //支付成功
            //更新订单状态
            ordersService.paySuccess(reservationId,resultMap);
            return Result.wrapSuccessfulResult("success");
        }
        return Result.wrapSuccessfulResult("waiting");//继续等待，三秒扫描一次
    }
}
