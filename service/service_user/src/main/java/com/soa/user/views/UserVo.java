package com.soa.user.views;

import lombok.Data;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 20:00:46
 */
@Data
public class UserVo {
    private String userId;
    private String name;
    private String password;
    private String phoneNumber;
    private String openId;
    private String email;
    private String salt;
}
