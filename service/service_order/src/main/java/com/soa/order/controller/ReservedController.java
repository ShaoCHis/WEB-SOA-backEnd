package com.soa.order.controller;

import com.soa.order.service.ReservationService;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-29 22:30:18
 */
@RestController
@RequestMapping("/orders/reserved")
@CrossOrigin
public class ReservedController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/patient/{patientId}")
    public Result haveReserved(@PathVariable String patientId){
        boolean flag = reservationService.queryHaveReserved(patientId);
        if(flag)
            return Result.wrapSuccessfulResult("exist");
        else
            return Result.wrapErrorResult("not exist");
    }

}
