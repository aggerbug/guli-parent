package com.atguigu.eduservice.client.reveal;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileClient implements VodClient {
    @Override
    public R removeAlyVoide(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> list) {
        return R.error().message("删除多个视频出错了");
    }
}
