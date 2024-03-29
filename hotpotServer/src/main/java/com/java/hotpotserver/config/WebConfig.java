package com.java.hotpotserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Value("${upload.accessPath}")
//    String accessPath;
//
//    @Value("${upload.localPath}")
//    String localPath;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(accessPath + "**").addResourceLocations("file:" + localPath);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {  //解决web端的跨域访问问题
        registry.addMapping("/*/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }
}
