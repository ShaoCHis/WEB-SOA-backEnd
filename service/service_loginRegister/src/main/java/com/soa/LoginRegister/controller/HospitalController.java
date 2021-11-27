package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.model.Administrator;
import com.soa.LoginRegister.model.Hospital;
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

/**
 * @ program: demo
 * @ description: 医院登录
 * @ author: ShenBo
 * @ date: 2021-11-18 09:13:16
 */
@RestController
@RequestMapping(path = "api/hospital")
@Api(value="医院登录",tags = "医院登录",description = "id和code均可")
public class HospitalController {
    @Autowired
    AuthenticationService authenticationService;

    @ApiOperation(value = "医院登陆")
    @PostMapping(path="/session")
    public @ResponseBody
    Result<Hospital> getLoginToken(@RequestBody Hospital body,
                                   HttpServletResponse response) {
        String sessionId;
        if(body.getId()!=null)
            sessionId=authenticationService.createHospSession(body.getId(),body.getPassword(),1);
        else
            sessionId=authenticationService.createHospSession(body.getCode(),body.getPassword(),2);

        if(sessionId==null){
            response.setStatus(401);
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        ResponseCookie responseCookie = ResponseCookie.from("hospitalSessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        Hospital hospital;
        if(body.getId()!=null)
            hospital=authenticationService.getHospital(sessionId,1);
        else
            hospital=authenticationService.getHospital(sessionId,2);
        return Result.wrapSuccessfulResult(hospital);
    }
}
