package com.java.hotpotserver.config;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//阿里云服务器的配置设置，便于连接
@Configuration
public class OssClientConfig {

    @Bean
    public OSSClient createOssClient() {
        return (OSSClient) new OSSClientBuilder().build("oss-cn-nanjing.aliyuncs.com",
                "LTAI5tLfMy3KSnVCeUAZ5aPa",
                "c6FxIFrUkJuvK76AiLXPryygGgaSab"); //设置Key， secret等
    }

}