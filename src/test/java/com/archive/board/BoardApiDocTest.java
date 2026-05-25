package com.archive.board;

import com.archive.board.dto.CreateBoardRequest;
import com.archive.common.GlobalExceptionHandler;
import com.archive.support.ApiDocHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
class BoardApiDocTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new BoardController(new BoardService()))
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("게시글 생성 성공")
    void create_board_success() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest("첫 번째 게시글", "게시글 내용입니다.");

        mockMvc.perform(post("/api/boards")
                        .header("X-Member-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.boardId").value(1))
                .andExpect(jsonPath("$.title").value("첫 번째 게시글"))
                .andExpect(jsonPath("$.content").value("게시글 내용입니다."))
                .andExpect(jsonPath("$.authorId").value(1))
                .andDo(document("board/create-success",
                        requestHeaders(
                                headerWithName("X-Member-Id").description("인증된 회원 ID (Gateway에서 주입)")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목 (최대 100자)").type("String"),
                                fieldWithPath("content").description("게시글 내용").type("String")
                        ),
                        responseFields(
                                fieldWithPath("boardId").description("생성된 게시글 ID").type("Number"),
                                fieldWithPath("title").description("게시글 제목").type("String"),
                                fieldWithPath("content").description("게시글 내용").type("String"),
                                fieldWithPath("authorId").description("작성자 회원 ID").type("Number"),
                                fieldWithPath("createdAt").description("생성 시간 (ISO 8601)").type("String")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 생성 실패 - 미인증")
    void create_board_fail_unauthorized() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest("제목", "내용");

        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andDo(document("board/create-fail-unauthorized",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목").type("String"),
                                fieldWithPath("content").description("게시글 내용").type("String")
                        ),
                        responseFields(ApiDocHelper.errorResponseFields())
                ));
    }
}
