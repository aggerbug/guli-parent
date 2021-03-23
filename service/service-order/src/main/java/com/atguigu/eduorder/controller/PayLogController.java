package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-21
 */
@CrossOrigin
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {

    @Resource
    private PayLogService payLogService;

    @GetMapping("/creatNative/{orderNo}")
    public R creatNative(@PathVariable String orderNo){
        Map map=payLogService.createNative(orderNo);
        return R.ok().data(map);
    }
}

