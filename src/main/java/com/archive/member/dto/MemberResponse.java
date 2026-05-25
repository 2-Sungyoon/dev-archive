package com.archive.member.dto;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String email,
        String nickname,
        LocalDateTime createdAt
) {}
