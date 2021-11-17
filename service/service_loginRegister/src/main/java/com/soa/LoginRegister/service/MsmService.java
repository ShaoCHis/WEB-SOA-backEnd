package com.soa.LoginRegister.service;

import org.springframework.stereotype.Service;
import org.apache.commons.mail.HtmlEmail;

/**
 * @ program: demo
 * @ description: MsmService
 * @ author: ShenBo
 * @ date: 2021-11-17 13:27:31
 */
@Service
public class MsmService {
    public boolean send(String param, String userEmail) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.163.com");//126邮箱为smtp.126.com,163邮箱为smtp.163.com，QQ为smtp.qq.com
            email.setCharset("UTF-8");
            email.addTo(userEmail);// 收件地址

            email.setFrom("registerWEB2021@163.com", "EmailIdentifyCode");//邮箱地址和用户名
            email.setAuthentication("registerWEB2021@163.com", "WHCWZULLIJIUKJIL");//邮箱地址和客户端授权码

            email.setSubject("挂号系统验证码");//此处填写邮件名，邮件名可任意填写
            email.setMsg("欢迎使用我们的医疗预约挂号系统，您的验证码是:" + param+"。有效时长为5分钟，请在限定时间内输入，感谢您的使用！");//此处填写邮件内容
            email.send();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
