package com.example.project.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.entity.Account;
import com.example.project.entity.Role;
import com.example.project.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Datalnitializer {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    // 서버 시작할 때 실행되는 메소드
    @Bean
    CommandLineRunner initData() {
        return args -> {
            // 계정이 1개 이상 생성된 경우 실행 X
            if(accountRepository.count() > 0){
                return;
            }

            accountRepository.save(
                Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("1234"))
                        .name("관리자")
                        .role(Role.ADMIN)
                        .build()
            );
            accountRepository.save(
                Account.builder()
                        .username("user1")
                        .password(passwordEncoder.encode("1234"))
                        .name("뭉치")
                        .role(Role.USER)
                        .build()
            );
            accountRepository.save(
                Account.builder()
                        .username("user2")
                        .password(passwordEncoder.encode("1234"))
                        .name("멍구")
                        .role(Role.USER)
                        .build()
            );

            
        };
    }
    
}
