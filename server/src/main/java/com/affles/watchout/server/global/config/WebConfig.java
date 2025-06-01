package com.affles.watchout.server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8081")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키 포함 허용
                .maxAge(3600);
    }
}
