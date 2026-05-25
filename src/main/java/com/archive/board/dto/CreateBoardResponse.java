package com.archive.board.dto;

import java.time.LocalDateTime;

public record CreateBoardResponse(
        Long boardId,
        String title,
        String content,
        Long authorId,
        LocalDateTime createdAt
) {}
