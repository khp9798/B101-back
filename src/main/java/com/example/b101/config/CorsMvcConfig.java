package com.example.b101.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정을 담당하는 클래스.
 * Spring MVC의 WebMvcConfigurer를 구현하여 CORS 관련 정책을 설정합니다.
 */
@Configuration // Spring Context에 이 클래스를 설정 빈으로 등록합니다.
public class CorsMvcConfig implements WebMvcConfigurer {

    /**
     * CORS 매핑 설정 메서드.
     * 특정 경로나 전체 경로에 대해 CORS 정책을 설정할 수 있습니다.
     *
     * @param registry CORS 설정을 등록하는 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","http://localhost:8080") // 명시적 도메인 허용
                .allowedMethods("*") // 모든 HTTP 메서드 허용
                .allowCredentials(true) // 인증 정보 포함 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("Authorization", "Set-Cookie"); // 노출 헤더 설정
    }
}
