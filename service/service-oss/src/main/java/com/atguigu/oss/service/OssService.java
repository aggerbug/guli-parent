package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    public String uploadFileAvater(MultipartFile file);
}
