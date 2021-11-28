package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.model.*;
import com.soa.LoginRegister.service.AuthenticationService;
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
@RequestMapping(path = "/loginRegister/administrators")
@Api(value="管理员登录",tags = "管理员登录",description = "管理员登录")
public class AdministratorController {
    @Autowired
    AuthenticationService authenticationService;

    @ApiOperation(value = "管理员登陆")
    @PostMapping(path="/session")
    public @ResponseBody
    Result<Administrator> getLoginToken(@RequestBody Administrator body,
                               HttpServletResponse response) {

        String sessionId=authenticationService.createAdminSession(body.getId(),body.getPassword());
        if(sessionId==null){
            response.setStatus(401);
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        ResponseCookie responseCookie = ResponseCookie.from("adminSessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        Administrator administrator=authenticationService.getAdministrator(sessionId);
        return Result.wrapSuccessfulResult(administrator);
    }
}
