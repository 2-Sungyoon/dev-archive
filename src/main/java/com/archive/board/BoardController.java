package com.archive.board;

import com.archive.board.dto.CreateBoardRequest;
import com.archive.board.dto.CreateBoardResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<CreateBoardResponse> create(
            @RequestHeader(value = "X-Member-Id", required = false) Long memberId,
            @Valid @RequestBody CreateBoardRequest request) {
        CreateBoardResponse response = boardService.create(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
