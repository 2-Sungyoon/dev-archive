package com.archive.auth.dto;

public record SignupResponse(
        Long memberId,
        String email,
        String nickname
) {}
