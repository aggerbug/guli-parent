package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {


    @Resource
    private OssService ossService;

    @PostMapping("/uplocald")
    public R uplocadOssFile(MultipartFile file){//上传文件
        String url=ossService.uploadFileAvater(file);
        return R.ok().data("url",url);
    }


}
