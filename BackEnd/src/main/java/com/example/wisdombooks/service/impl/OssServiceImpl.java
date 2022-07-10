package com.example.wisdombooks.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.wisdombooks.service.OssService;
import com.example.wisdombooks.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    // 上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String fileName = "";
        String picUrl = "";
        try {
            // 创建oss实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //原始文件名
            String originalFilename = file.getOriginalFilename();//abc.jpg
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
            fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
            // 获取当前日期(将上传文件按日期分类)
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            // 获取上传文件流
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);

            // 拼接图片路径
            picUrl = "https://" + bucketName + "." + endpoint + "/" + fileName;
            // 关闭ossClient
            ossClient.shutdown();
            return picUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
