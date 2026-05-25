package com.archive.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {}
