package com.atguigu.eduservice.client;

import com.atguigu.eduservice.client.reveal.UcenterClientImpl;
import com.atguigu.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getInfo/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);
}
