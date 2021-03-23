package com.atguigu.smsservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.smsservice.util.SMSUtils;
import com.atguigu.smsservice.util.RandomUtil;
import com.atguigu.smsservice.util.ValidateCodeUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edusms/msm")
@CrossOrigin
public class SmsController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping("/send/{phone}")
    public R sendPhone(@PathVariable String phone){
        String phoneRedis = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(phoneRedis)){
            return R.ok().message("请勿频繁操作，短信已发送，请等待");
        }
        try {
            String code= ValidateCodeUtils.generateValidateCode(4).toString();
      //      SMSUtils.sendShortMessage(phone,code);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok().message("短信发送成功");
        } catch (Exception e) {
            return R.error().message("短信发送失败");
        }
    }
}
