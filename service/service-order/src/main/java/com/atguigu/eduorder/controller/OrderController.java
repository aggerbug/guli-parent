package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-21
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/creatOder/{courseId}")
    public R creatOder(@PathVariable String courseId, HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        String order = orderService.saveOrder(courseId, id);
        return R.ok().data("orderId",order);
    }

    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order one = orderService.getOne(wrapper);
        return R.ok().data("item",one);

    }

}

