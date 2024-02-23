package com.java.hotpotserver.service.impl;

import com.aliyun.oss.OSSClient;
import com.java.hotpotserver.service.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
@Service
public class IOssServiceImpl implements IOssService {
    @Autowired
    private OSSClient ossClient;

    @Override
    public String upload(MultipartFile file) { //上传图片到阿里云
        String bucketName = "hudilao";
        try {
            String objectName = getBucketName(file.getOriginalFilename());
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, file.getInputStream());
            return "https://" +bucketName+"."+ ossClient.getEndpoint().getHost() + "/" + objectName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }
    //图片格式，可以自己加
    private static final String[] imageExtension = new String[]{ ".jpg", ".jpeg",  ".png",".webp"};

    private String getBucketName(String url) {
        String ext = "";
        for(String extItem:imageExtension){
            if(url.contains(extItem)){
                ext = extItem;
                break;
            }
        }
        //日期将作为文件夹名，每天的图片会存到当天的文件夹内
        //文件名为uuid值
        return LocalDate.now() +"/"+ UUID.randomUUID() +ext;
    }



}
