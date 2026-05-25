package com.archive.auth;

import com.archive.auth.dto.LoginRequest;
import com.archive.auth.dto.LoginResponse;
import com.archive.auth.dto.SignupRequest;
import com.archive.auth.dto.SignupResponse;
import com.archive.common.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public LoginResponse login(LoginRequest request) {
        // Mock: 특정 계정만 성공
        if (!"user@archive.com".equals(request.email()) || !"password123".equals(request.password())) {
            throw new AuthenticationException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return new LoginResponse(
                "eyJhbGciOiJIUzI1NiJ9.mock-access-token",
                "eyJhbGciOiJIUzI1NiJ9.mock-refresh-token",
                3600L
        );
    }

    public SignupResponse signup(SignupRequest request) {
        // Mock: 중복 이메일 체크
        if ("existing@archive.com".equals(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        return new SignupResponse(1L, request.email(), request.nickname());
    }
}
