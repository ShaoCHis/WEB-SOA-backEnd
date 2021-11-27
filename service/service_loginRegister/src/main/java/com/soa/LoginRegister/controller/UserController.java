package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.model.User;
import com.soa.LoginRegister.service.*;
import com.soa.utils.error.UserAlreadyExistedError;
import com.soa.utils.error.UserNotExistedError;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/users")
@Api(value="用户登录",tags = "用户登录注册",description = "用户登录注册")
public class UserController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户注册")
    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<User> addNewUser (@RequestBody User body) {
        User user=userService.addNewUser(body);
        if(user==null) return  Result.wrapErrorResult(new UserAlreadyExistedError());
        return Result.wrapSuccessfulResult(user);
    }

    @ApiOperation(value = "用户登陆")
    @PostMapping(path="/session")
    public @ResponseBody
    Result<User> getLoginToken(@RequestBody User body,
                               HttpServletResponse response) {

        String sessionId=authenticationService.createSessionId(body.getEmail(),body.getPassword());
        if(sessionId==null){
            response.setStatus(401);
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        ResponseCookie responseCookie = ResponseCookie.from("userSessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        User user=authenticationService.getUser(sessionId);
        return Result.wrapSuccessfulResult(user);
    }
}
