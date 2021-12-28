package com.soa.order.controller;

import com.soa.order.service.StatisticService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-28 11:10:53
 */
@RestController
@RequestMapping("/orders/statistic")
@CrossOrigin
@Api(value="统计流水",tags = "统计流水",description = "统计流水")
public class StatisticController {
    @Autowired
    StatisticService statisticService;

    @GetMapping("/test")
    public Result testMoney(){
        String fromDate="2019-01-01";
        String endDate="2021-12-28";
        int moneyTest = statisticService.getMoneyTest(fromDate, endDate);
        return Result.wrapSuccessfulResult(moneyTest);
    }

}
