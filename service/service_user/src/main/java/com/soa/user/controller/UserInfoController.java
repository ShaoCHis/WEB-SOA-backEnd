package com.soa.user.controller;

import com.soa.user.model.User;
import com.soa.user.service.UserInfoService;
import com.soa.user.views.UserVo;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ program: demo
 * @ description: userInfo
 * @ author: ShenBo
 * @ date: 2021-11-18 08:43:54
 */
@RestController
@RequestMapping("/user/Info")
@CrossOrigin
@Api(value="用户信息",tags = "用户信息",description = "用户信息")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value="根据用户id获取用户信息")
    @GetMapping("getUserInfo/{id}")
    public Result<User> getUserInfo(@PathVariable String id){
        User user=userInfoService.getById(id);
        if(user!=null)
            return Result.wrapSuccessfulResult(user);
        else
            return Result.wrapErrorResult("error!");
    }

    @ApiOperation(value="修改用户基本信息,用户id必须传")
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody UserVo userVo){
        boolean flag=userInfoService.updateUserInfo(userVo);
        if(flag)
            return Result.wrapSuccessfulResult("success");
        else
            return Result.wrapErrorResult("error");
    }
}
