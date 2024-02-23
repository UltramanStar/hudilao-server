package com.java.hotpotserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration //配置文件
@EnableAsync
public class Appconfig {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); //设置线程池
        executor.setCorePoolSize(5);//线程池的大小
        executor.setMaxPoolSize(10);  //最大容量
        executor.setQueueCapacity(25); //队列的容量
        return executor;
    }
}

