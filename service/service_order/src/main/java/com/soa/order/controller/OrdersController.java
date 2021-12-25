package com.soa.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soa.order.service.OrdersService;
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

    @ApiOperation(value="根据patientId和卡的type查询卡上的余额，type:1为就诊卡2为社保卡3为医保卡" +
            "本api暂不可用")
    @GetMapping("money/{patientId}/{type}")
    public Result GetBalance(@PathVariable String patientId,
                             @PathVariable Integer type){
        if(type==1)
        {
            //医院就诊卡
            String url="http://139.196.194.51:18080/api/patients/"+patientId;
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody().getJSONObject("data");
//            JSON.parseObject(String.valueOf(json), .class);


        }else if(type==2){
            //社保卡
            String url="http://139.196.194.51:18081/api/patients/all";

        }else if(type==3){
            //医保卡


        }

        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据reservationId和patientId和卡的type，使用卡余额付款，这个api还不能用")
    @PostMapping("cardPay/{reservationId}/{patientId}/{type}")
    public Result cardPay(@PathVariable String reservationId,
                          @PathVariable String patientId,
                          @PathVariable Integer type) {

        //给reservation记录卡信息
        //给orders表记录支付类型
        // type：1为医院就诊卡，2为社保卡，3为医保卡

        return Result.wrapSuccessfulResult("success");

        //支付失败是因为卡余额不足，提示前端



    }

}
