package com.soa.statistic.controller;

import com.soa.statistic.client.MoneyFeignClient;
import com.soa.statistic.view.HospitalQueryVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MoneyFeignClient moneyFeignClient;

    @ApiOperation(value="根据医院id和起止日期查询医院流水")
    @PostMapping("/getHospitalMoneyByDay")
    public Result getHospitalMoneyByDay(@RequestBody HospitalQueryVo hospitalQueryVo){
        return moneyFeignClient.queryHospitalMoney(hospitalQueryVo.getReserveDateBegin(),
                hospitalQueryVo.getReserveDateEnd(),hospitalQueryVo.getHospitalId());
    }

    @ApiOperation(value="根据年份和月份查询当月30天医院流水")
    @GetMapping("/getHospMonthEverydayMoney/{year}/{month}/{hospitalId}")
    public Result getHospMonthEverydayMoney(@PathVariable String year,
                                            @PathVariable String month,
                                            @PathVariable String hospitalId){
        int tmpYear = Integer.parseInt(year);
        int tmpMonth = Integer.parseInt(month);

        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        int flag=0;//只有闰年2月才变成1，其他都是0

        if(((tmpYear%4==0 && tmpYear%100!=0) || (tmpYear%400==0))&&tmpMonth==2)
            flag=1;//闰年2月加一天

        String fromDate=year+"-"+month+"-01";
        String endDate=year+"-"+month+"-"+String.valueOf(days[tmpMonth-1]+flag);
        return moneyFeignClient.queryHospitalMoney(fromDate,endDate,hospitalId);
    }

    @ApiOperation(value="根据年份查询365天医院流水")
    @GetMapping("/getHospYearEverydayMoney/{year}/{hospitalId}")
    public Result getHospYearEverydayMoney(@PathVariable String year,
                                           @PathVariable String hospitalId){
        String fromDate=year+"-01-01";
        String endDate=year+"-12-31";
        return moneyFeignClient.queryHospitalMoney(fromDate,endDate,hospitalId);
    }

}
