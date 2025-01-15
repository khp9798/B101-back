package com.example.b101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/user/**").permitAll()
//                        .requestMatchers("/my/**").hasRole("USER")
                        .anyRequest().permitAll()
                );

        http
                .formLogin((auth) -> auth.loginPage("/login")
                .loginProcessingUrl("/api/user/login")
                        .failureUrl("/login-fail")
                        .defaultSuccessUrl("/index", true) // 로그인 성공 시 리다이렉트될 경로
                        .usernameParameter("nickname") // 기본 username을 nickname으로 변경
                        .passwordParameter("password") // 비밀번호 필드 설정
                        .permitAll());

        http
                .csrf().disable();
        return http.build();
    }
}
