package com.soa.LoginRegister.controller;

import com.soa.LoginRegister.service.MsmService;
import com.soa.LoginRegister.utils.RandomUtil;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("send/{email}")
    public Result sendMsm(@PathVariable String email) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) {
            return Result.wrapSuccessfulResult("Success!");
        }
        //2 如果redis获取不到，进行发送
        //生成随机值
        code = RandomUtil.getFourBitRandom();
        //调用service发送短信的方法
        boolean isSend = msmService.send(code,email);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return Result.wrapSuccessfulResult("Success!");
        } else {
            return Result.wrapErrorResult("Fail!");
        }
    }
}
