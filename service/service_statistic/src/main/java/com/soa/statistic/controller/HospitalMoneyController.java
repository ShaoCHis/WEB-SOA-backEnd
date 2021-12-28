package com.soa.statistic.controller;

import com.soa.statistic.view.HospitalQueryVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 22:04:14
 */
@RestController
@RequestMapping("/statistic/hospital")
@CrossOrigin
@Api(value="医院流水",tags = "医院流水",description = "医院流水")
public class HospitalMoneyController {


    @ApiOperation(value="根据医院id和起止日期查询医院流水(日期String格式为xxxx-xx-xx，" +
            "个位数的月份需要补十位的0，例子：2022-01-01)")
    @PostMapping("/getHospitalMoneyByDay")
    public Result getHospitalMoneyByDay(@RequestBody HospitalQueryVo hospitalQueryVo){

        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据年份和月份查询当月30天医院流水")
    @GetMapping("/getHospMonthEverydayMoney/{year}/{month}")
    public Result getHospMonthEverydayMoney(@PathVariable String year,
                                            @PathVariable String month){
//调上面的
        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value = "根据年份查询12个月的医院流水")
    @GetMapping("/getHospYearEveryMonth/{year}")
    public Result getHospYearEveryMonth(@PathVariable String year){
//调上面的，然后根据每月日子算一下
        return Result.wrapSuccessfulResult("success");
    }

    @ApiOperation(value="根据年份查询365天医院流水")
    @GetMapping("/getHospYearEverydayMoney/{year}")
    public Result getHospYearEverydayMoney(@PathVariable String year){
//调第一个
        return Result.wrapSuccessfulResult("success");
    }

}
