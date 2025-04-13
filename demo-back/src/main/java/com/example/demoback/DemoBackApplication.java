package com.example.demoback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("com.example.demoback.mapper")
public class DemoBackApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DemoBackApplication.class, args);
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD","PATCH","TRACE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Value("${upload.diskPath}")
    String diskPath;
    @Value("${upload.webPath}")
    String webPath;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(webPath+"**").addResourceLocations("file:"+diskPath);
    }


}


