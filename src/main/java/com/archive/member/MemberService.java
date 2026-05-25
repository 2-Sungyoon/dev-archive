package com.archive.member;

import com.archive.common.NotFoundException;
import com.archive.member.dto.MemberResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberService {

    public MemberResponse getMyInfo(Long memberId) {
        // Mock 데이터
        if (memberId == null || memberId <= 0) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        return new MemberResponse(
                memberId,
                "user@archive.com",
                "아카이브유저",
                LocalDateTime.of(2025, 1, 15, 10, 30, 0)
        );
    }
}
