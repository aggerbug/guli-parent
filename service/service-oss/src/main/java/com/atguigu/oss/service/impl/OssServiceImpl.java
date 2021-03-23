package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.SimpleBeanInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvater(MultipartFile file) {
        String endpoint= ConstantPropertiesUtils.END_POIND;
        String accessKeyId=ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;


        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            InputStream inputStream=file.getInputStream();//获取输入流
            String fileName=file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName=uuid+fileName;

            String data = new DateTime().toString("yyyy/MM/dd/");

            fileName=data+fileName;

            ossClient.putObject(bucketName,fileName,inputStream);

            ossClient.shutdown();

            //https://atguigusun.oss-cn-beijing.aliyuncs.com/4.jpg
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
