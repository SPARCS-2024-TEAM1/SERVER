package com.sparcs.team1.global.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:8081", "http://localhost:5173",
                        "https://sparcs-team1.p-e.kr", "https://sparcs-ai-team-1-client.vercel.app/")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 애플리케이션의 HTTP 메시지 컨버터를 완전히 새로 설정
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 기존 컨버터들을 모두 제거 (외부 라이브러리 코틀린 컨버터와의 충돌을 피하기 위함)
        converters.clear();

        // Swagger 오류 해결을 위한 ByteArrayHttpMessageConverter 추가 (add 순서 중요)
        converters.add(new ByteArrayHttpMessageConverter());

        // Jackson 라이브러리를 사용하는 컨버터를 추가
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
