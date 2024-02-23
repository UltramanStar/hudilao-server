package com.java.hotpotserver.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssService {
    public String upload(MultipartFile file); //上传图片到阿里云
}
