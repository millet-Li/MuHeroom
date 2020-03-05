package com.room.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyConfiguration implements WebMvcConfigurer {

    @Value("${mydata.patha}")
    private String image;
    @Value("${mydata.pathb}")
    private String life;
    @Value("${mydata.pathc}")
    private String room;

    //配置文件路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations(image);
        registry.addResourceHandler("/life/**").addResourceLocations(life);
        registry.addResourceHandler("/rooms/**").addResourceLocations(room);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
