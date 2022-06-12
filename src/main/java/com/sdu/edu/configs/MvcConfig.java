package com.sdu.edu.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("C://Users/Shyngys/Desktop/")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:"+uploadPath+"libs/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/course/**")
                .addResourceLocations("file:"+uploadPath+"course/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }
}
