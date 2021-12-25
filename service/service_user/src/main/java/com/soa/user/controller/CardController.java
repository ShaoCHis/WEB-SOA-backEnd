package com.soa.user.controller;

import com.soa.user.model.Card;
import com.soa.user.model.Patient;
import com.soa.user.service.CardService;
import com.soa.user.service.PatientService;
import com.soa.user.service.UserInfoService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 21:48:34
 */
@RestController
@RequestMapping("/user/cards")
@CrossOrigin
@Api(value="卡信息",tags = "卡信息",description = "卡信息")
public class CardController {
    //根据病人id（身份证号）查询他的卡列表
    @Autowired
    PatientService patientService;

    @ApiOperation(value="根据病人id（身份证号）查询他的卡列表," +
            "卡只有一种类型，但是一个病人可以有很多家医院的卡")
    @GetMapping("getPatientCards/{id}")
    public Result getPatientCards(@PathVariable String id){
        Patient patient=patientService.getById(id);
        if(patient!=null)
        {
            List<Card> cards = patient.getCards();
            return Result.wrapSuccessfulResult(cards);
        }
        else
            return Result.wrapErrorResult("error");
    }

}