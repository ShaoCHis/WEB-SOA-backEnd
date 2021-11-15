package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.model.User;
import com.soa.LoginRegister.service.*;
import com.soa.utils.error.UserAlreadyExistedError;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<User> addNewUser (@RequestBody User body) {

        User user=userService.addNewUser(body);
        if(user==null) return  Result.wrapErrorResult(new UserAlreadyExistedError());
        return Result.wrapSuccessfulResult(user);
    }

    @PostMapping(path="/session")
    public @ResponseBody
    Result<User> getLoginToken(@RequestBody User body,
                               HttpServletResponse response) {

        String sessionId=authenticationService.createSessionId(body.getUserId(),body.getPassword());
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

        User user=authenticationService.getUser(sessionId);
        return Result.wrapSuccessfulResult(user);
    }
}
