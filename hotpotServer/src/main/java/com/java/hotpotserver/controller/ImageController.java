package com.java.hotpotserver.controller;

import com.java.hotpotserver.service.IOssService;
import com.java.hotpotserver.vo.Result;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {
    @Autowired
    IOssService ossService;//阿里云oss服务器服务
    @PostMapping("/upload")//上传图片
    public Result setImage(MultipartFile file) throws IOException {
        return new Result(200,"文件上传成功",ossService.upload(file));
    }
}
