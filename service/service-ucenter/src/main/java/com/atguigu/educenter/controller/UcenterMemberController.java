package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-17
 */
@Api(tags = "用户登录注册")
@CrossOrigin
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Resource
    private UcenterMemberService memberService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo member){
        String token=memberService.loginUser(member);
        return R.ok().data("token",token);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("获取token")
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @ApiOperation("通过token获取用户详情")
    @PostMapping("/getInfo/{id}")
    public UcenterMember getInfo(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(member,ucenterMember);
        return ucenterMember;
    }
    //远程调用接口，获取用户信息提交订单
    @GetMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable String id){
        UcenterMember byId = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(byId,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}

