package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.reveal.VodFileClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileClient.class)
@Component
public interface VodClient {
    //调用方法的mapper和接口方法
    @DeleteMapping("/eduvod/video/removeAlyVoide/{id}")
    public R removeAlyVoide(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deletebatch")
    public R deleteBatch(@RequestParam("list") List<String> list);
}
