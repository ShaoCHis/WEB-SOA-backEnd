package com.soa.user.controller;

import com.soa.user.service.CardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    CardService cardService;


}
