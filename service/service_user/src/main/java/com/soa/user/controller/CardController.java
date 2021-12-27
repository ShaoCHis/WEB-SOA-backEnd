package com.soa.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.user.model.Patient;
import com.soa.user.service.PatientService;
import com.soa.user.views.Card;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    @ApiOperation(value="根据病人id（身份证号）查询他的卡列表" +
            "卡的type：1为医院就诊卡，2为社保卡，3为医保卡")
    @GetMapping("getPatientCards/{id}")
    public Result getPatientCards(@PathVariable String id){
        int size=3;
        String[] myUrl =new String[size];
        myUrl[0]="http://139.196.194.51:18080/api/patients/card/"+id;//就诊卡
        myUrl[1]="http://139.196.194.51:18081/api/patients/card/"+id;//社保卡
        myUrl[2]="http://139.196.194.51:18081/api/patient2s/card/"+id;//医保卡

        List<Card> cards = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            //查询三种卡
            RestTemplate restTemplate = new RestTemplate();
            Object data = restTemplate.getForEntity(myUrl[i], JSONObject.class).getBody().get("data");

            if(data!=null)
            {
                String cardId=(String)data;
                cards.add(new Card(cardId,id,i+1));
            }
        }
        return Result.wrapSuccessfulResult(cards);
    }

}