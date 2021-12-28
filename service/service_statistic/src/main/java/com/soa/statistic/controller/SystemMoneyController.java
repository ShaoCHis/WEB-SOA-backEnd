package com.soa.statistic.controller;

import com.soa.statistic.client.MoneyFeignClient;
import com.soa.statistic.view.SystemQueryVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MoneyFeignClient moneyFeignClient;

    @ApiOperation(value="根据起止日期查询平台流水")
    @PostMapping("/getSystemMoneyByDay")
    public Result getSystemMoneyByDay(@RequestBody SystemQueryVo systemQueryVo){
        return moneyFeignClient.querySystemMoney(systemQueryVo.getReserveDateBegin(), systemQueryVo.getReserveDateEnd());
    }

    @ApiOperation(value="根据年份和月份查询当月30天的平台流水")
    @GetMapping("/getSystemMonthEverydayMoney/{year}/{month}")
    public Result getSystemMonthEverydayMoney(@PathVariable String year,
                                            @PathVariable String month){
        int tmpYear = Integer.parseInt(year);
        int tmpMonth = Integer.parseInt(month);

        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        int flag=0;//只有闰年2月才变成1，其他都是0

        if(((tmpYear%4==0 && tmpYear%100!=0) || (tmpYear%400==0))&&tmpMonth==2)
            flag=1;//闰年2月加一天

        String fromDate=year+"-"+month+"-01";
        String endDate=year+"-"+month+"-"+String.valueOf(days[tmpMonth-1]+flag);
        return moneyFeignClient.querySystemMoney(fromDate,endDate);
    }

    @ApiOperation(value="根据年份查询365天平台流水")
    @GetMapping("/getSystemYearEverydayMoney/{year}")
    public Result getSystemYearEverydayMoney(@PathVariable String year){
        String fromDate=year+"-01-01";
        String endDate=year+"-12-31";
        return moneyFeignClient.querySystemMoney(fromDate,endDate);
    }

}
