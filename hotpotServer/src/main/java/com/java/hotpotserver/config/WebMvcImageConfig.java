package com.java.hotpotserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//配置文件，解决资源的映射问题
@Configuration
public class WebMvcImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  //解决资源的映射
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:images/");
    }
}
