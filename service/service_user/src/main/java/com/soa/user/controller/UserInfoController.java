package com.soa.user.controller;

import com.soa.user.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description: userInfo
 * @ author: ShenBo
 * @ date: 2021-11-18 08:43:54
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value="用户信息",tags = "用户信息",description = "用户信息")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

}
