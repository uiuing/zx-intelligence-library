package com.example.wisdombooks.controller;

import com.example.wisdombooks.common.R;
import com.example.wisdombooks.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/oss/fileOss")
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        // 获取上传文件
        String url = ossService.uploadFileAvatar(file);
        return R.success("上传成功!").add("url", url);
    }
}
