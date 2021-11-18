package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import service.UserInfoService;

/**
 * @ program: demo
 * @ description: userInfo
 * @ author: ShenBo
 * @ date: 2021-11-18 08:43:54
 */
@RestController
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

}
