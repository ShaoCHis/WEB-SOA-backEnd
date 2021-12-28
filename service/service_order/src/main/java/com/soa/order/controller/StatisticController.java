package com.soa.order.controller;

import com.soa.order.service.StatisticService;
import com.soa.order.views.StatisticResult;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-28 11:10:53
 */
@RestController
@RequestMapping("/orders/statistic")
@CrossOrigin
public class StatisticController {
    @Autowired
    StatisticService statisticService;

    @GetMapping("/querySystemMoney/{fromDate}/{endDate}")
    public Result querySystemMoney(@PathVariable String fromDate,
                                   @PathVariable String endDate){
        Map<String, Integer> answer = statisticService.getSystemMoney(fromDate, endDate);
        return Result.wrapSuccessfulResult(answer);
    }

    @GetMapping("/queryHospitalMoney/{fromDate}/{endDate}/{hospitalId}")
    public Result queryHospitalMoney(@PathVariable String fromDate,
                                     @PathVariable String endDate,
                                     @PathVariable String hospitalId){
        Map<String, Integer> answer = statisticService.getHospitalMoney(fromDate, endDate, hospitalId);
        return Result.wrapSuccessfulResult(answer);
    }

}
