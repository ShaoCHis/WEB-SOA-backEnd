package com.soa.oss.controller;

import com.soa.oss.service.OssService;
import com.soa.utils.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ program: demo
 * @ description: OssController
 * @ author: ShenBo
 * @ date: 2021-11-16 08:46:12
 */
@RestController
@RequestMapping("/oss/fileoss")
@CrossOrigin
@Api(value="上传图片",tags = "上传图片",description = "上传医院logo")
public class OssController {
    @Autowired
    OssService ossService;

    @PostMapping
    public Result uploadPic(MultipartFile file)
    {
        String url = ossService.uploadFilePic(file);
        return Result.wrapSuccessfulResult("urlOfPicture",url);
    }
}
