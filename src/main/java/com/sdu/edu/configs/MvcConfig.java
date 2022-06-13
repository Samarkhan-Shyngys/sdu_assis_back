package com.sdu.edu.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.user-image}")
    private String avaPath;

    @Value("${file.upload-course}")
    private String coursePath;

    @Value("${file.upload-book}")
    private String bookPath;

    @Value("${file.upload-certificate}")
    private String certificatePath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ava/**")
                .addResourceLocations("file:"+avaPath+"/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/course/**")
                .addResourceLocations("file:"+coursePath+"/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/book/**")
                .addResourceLocations("file:"+bookPath+"/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/certificate/**")
                .addResourceLocations("file:"+certificatePath+"/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }
}
