package com.example.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.AccountDTO;
import com.example.project.dto.LoginDTO;
import com.example.project.entity.Account;
import com.example.project.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    // Spring Security 인증을 수행하는 객체
    private final AuthenticationManager authenticationManager;

    // 로그인 수행 메소드
    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @RequestBody LoginDTO loginDTO,
        HttpServletRequest request
    ) {

        // 인증 매니저에게 username, password를 전송
        Authentication authentication = 
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
        
        // 인증 정보를 저장할 context 생성
        SecurityContext context = 
            SecurityContextHolder.createEmptyContext();
        
        // 인증 성공한 Authentication 객체 저장
        context.setAuthentication(authentication);

        // 현재 스레드의 ContextHolder에 등록
        SecurityContextHolder.setContext(context);

        // 세션이 없으면 생성
        HttpSession session = request.getSession(true);

        // SecurityContext를 세션에 저장
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        return ResponseEntity.ok().build();

    }

    // 로그아웃 기능
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    // 로그인 사용자 정보 조회
    @GetMapping("/account/me")
    public ResponseEntity<AccountDTO> me(Authentication authentication) {
        // Authentication : 인증 성공(로그인 성공)한 사용자의 인증 정보 저장 객체

        // 인증된 사용자가 아니면 접근 거부
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();
        // principal : 로그인한 사용자 정보가 저장되어 있음

        Account account = userDetails.getAccount();

        AccountDTO dto = AccountDTO.builder()
            .accountId(account.getAccountId())
            .username(account.getUsername())
            .name(account.getName())
            .role(account.getRole())
            .build();

        // * Thymeleaf 활용한 방식은 클라이언트로 account 데이터를
        // 전송할 필요가 없음(인증 객체에서 직접 참조하기 때문에)
        // * React를 활용한 방식은 인증 객체를 직접 활용할 수 없기 때문에
        // dto 데이터를 전송하고 클라이언트에서 정보를 관리한다.
        return ResponseEntity.ok(dto);
    }
    
}
