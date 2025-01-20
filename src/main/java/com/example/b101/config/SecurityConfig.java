package com.example.b101.config;

import com.example.b101.domain.User;
import com.example.b101.dto.CustomUserDetails;
import com.example.b101.jwt.JWTFilter;
import com.example.b101.jwt.JWTUtil;
import com.example.b101.oauth2.CustomSuccessHandler;
import com.example.b101.response.ApiResponseUtil;
import com.example.b101.service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환용

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 세션 사용 안 함 (JWT 활용)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 자체 로그인 설정
                .formLogin(form -> form
                        .loginProcessingUrl("/api/user/login") // 로그인 처리 URL
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");

                            // 사용자 정보 가져오기
                            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                            // JWT 토큰 생성
                            String token = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getRole(), 1000L * 60 * 60);

                            // 응답 데이터 생성
                            var responseData = Map.of(
                                    "token", token,
                                    "nickname", Optional.ofNullable(userDetails.getNickname()).orElse("익명 사용자")
                            );

                            // ApiResponseUtil로 성공 응답 생성
                            var apiResponse = ApiResponseUtil.success(
                                    responseData,          // 데이터
                                    "로그인 성공",          // 메시지
                                    HttpStatus.OK,         // HTTP 상태
                                    request.getRequestURI() // 요청 경로
                            );

                            // 응답 출력
                            response.getWriter().write(objectMapper.writeValueAsString(apiResponse.getBody()));
                        })

                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");

                            // 응답 상태 설정
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            // ApiResponseUtil로 실패 응답 생성
                            var apiResponse = ApiResponseUtil.failure(
                                    "로그인 실패: " + exception.getMessage(), // 실패 메시지
                                    HttpStatus.UNAUTHORIZED,               // HTTP 상태
                                    request.getRequestURI()                // 요청 경로
                            );

                            // 응답 출력
                            response.getWriter().write(objectMapper.writeValueAsString(apiResponse.getBody()));
                        })

                )
                // OAuth2 소셜 로그인 설정
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) // 사용자 정보 가져오기
                        .successHandler(customSuccessHandler) // 성공 핸들러
                )
                // JWT 필터 추가
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 경로별 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/user/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
