package com.archive.board;

import com.archive.board.dto.CreateBoardRequest;
import com.archive.board.dto.CreateBoardResponse;
import com.archive.common.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoardService {

    public CreateBoardResponse create(Long memberId, CreateBoardRequest request) {
        if (memberId == null || memberId <= 0) {
            throw new AuthenticationException("로그인이 필요합니다.");
        }
        return new CreateBoardResponse(
                1L,
                request.title(),
                request.content(),
                memberId,
                LocalDateTime.of(2025, 1, 15, 10, 30, 0)
        );
    }
}
