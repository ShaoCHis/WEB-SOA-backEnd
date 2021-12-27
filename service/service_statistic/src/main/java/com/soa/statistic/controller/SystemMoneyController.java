package com.soa.statistic.controller;

import com.soa.statistic.view.HospitalQueryVo;
import com.soa.statistic.view.SystemQueryVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 22:06:22
 */
@RestController
@RequestMapping("/statistic/system")
@CrossOrigin
@Api(value="平台流水",tags = "平台流水",description = "平台流水")
public class SystemMoneyController {

//
//    @ApiOperation(value="根据起止日期查询平台流水")
//    @PostMapping("/getSystemMoney")
//    public Result getSystemMoney(@RequestBody SystemQueryVo systemQueryVo){
//
//        return Result.wrapSuccessfulResult("success");
//    }

}
