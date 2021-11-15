package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.model.*;
import com.soa.LoginRegister.service.AuthenticationService;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/administrators")
public class AdministratorController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(path="/session")
    public @ResponseBody
    Result<Administrator> getLoginToken(@RequestBody Administrator body,
                               HttpServletResponse response) {

        String sessionId=authenticationService.createSessionId(body.getId(),body.getPassword());
        if(sessionId==null){
            response.setStatus(401);
            return null;
        }
        ResponseCookie responseCookie = ResponseCookie.from("sessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        Administrator administrator=authenticationService.getAdministrator(sessionId);
        return Result.wrapSuccessfulResult(administrator);
    }
}
