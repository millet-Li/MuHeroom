package com.room.utils;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyConfiguration implements WebMvcConfigurer {
    /*开发环境*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/D:/room/image/");
        registry.addResourceHandler("/life/**").addResourceLocations("file:/D:/room/life/");
        registry.addResourceHandler("/room/**").addResourceLocations("file:/D:/room/room/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
    /*生产环境*/
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/C:/room/image/");
        registry.addResourceHandler("/life/**").addResourceLocations("file:/C:/room/life/");
        registry.addResourceHandler("/room/**").addResourceLocations("file:/C:/room/room/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }*/
}
