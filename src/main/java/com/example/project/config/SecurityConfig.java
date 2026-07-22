package com.example.project.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
// @EnableMethodeSecurity : 메소드 보안 기능 활성화 설정
// @PreAuthorize : 메소드 실행 전에 권한 검사
// @PostAuthorize : 메소드 실행 후에 보안 검사
public class SecurityConfig {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // React + REST API이므로 CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // CORS 허용
                .cors(Customizer.withDefaults())
                // URL 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                // Spring Security 기본 로그인 화면 사용 안함
                .formLogin(form -> form.disable())
                // Spring Security 기본 로그아웃 비활성화
                .logout(logout -> logout.disable())
                // HTTP Basic 인증 사용 안함
                .httpBasic(httpBasic -> httpBasic.disable());
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
