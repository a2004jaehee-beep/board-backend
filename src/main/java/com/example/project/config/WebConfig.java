package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer consConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
                        .allowedOrigins("http://localhost:5173") //react 개발 서버 도메인 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP 메소드 허용
                        .allowCredentials(true); // 자격 증명(쿠키 등) 허용 
            }
        };
    }
    
}
