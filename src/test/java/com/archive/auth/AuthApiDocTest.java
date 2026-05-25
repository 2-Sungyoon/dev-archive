package com.archive.auth;

import com.archive.common.GlobalExceptionHandler;
import com.archive.auth.dto.LoginRequest;
import com.archive.auth.dto.SignupRequest;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
class AuthApiDocTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(new AuthService()))
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest("user@archive.com", "password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.expiresIn").value(3600))
                .andDo(document("auth/login-success",
                        requestFields(
                                fieldWithPath("email").description("로그인 이메일").type("String"),
                                fieldWithPath("password").description("비밀번호").type("String")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("액세스 토큰 (JWT)").type("String"),
                                fieldWithPath("refreshToken").description("리프레시 토큰").type("String"),
                                fieldWithPath("expiresIn").description("토큰 만료 시간 (초)").type("Number")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 자격증명")
    void login_fail_invalid_credentials() throws Exception {
        LoginRequest request = new LoginRequest("user@archive.com", "wrong-password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andDo(document("auth/login-fail-credentials",
                        requestFields(
                                fieldWithPath("email").description("로그인 이메일").type("String"),
                                fieldWithPath("password").description("비밀번호 (잘못된 값)").type("String")
                        ),
                        responseFields(ApiDocHelper.errorResponseFields())
                ));
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() throws Exception {
        SignupRequest request = new SignupRequest("new@archive.com", "password123", "뉴유저");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.email").value("new@archive.com"))
                .andDo(document("auth/signup-success",
                        requestFields(
                                fieldWithPath("email").description("가입 이메일").type("String"),
                                fieldWithPath("password").description("비밀번호 (8~20자)").type("String"),
                                fieldWithPath("nickname").description("닉네임 (2~10자)").type("String")
                        ),
                        responseFields(
                                fieldWithPath("memberId").description("생성된 회원 ID").type("Number"),
                                fieldWithPath("email").description("가입된 이메일").type("String"),
                                fieldWithPath("nickname").description("닉네임").type("String")
                        )
                ));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일")
    void signup_fail_duplicate_email() throws Exception {
        SignupRequest request = new SignupRequest("existing@archive.com", "password123", "유저");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andDo(document("auth/signup-fail-duplicate",
                        requestFields(
                                fieldWithPath("email").description("이미 가입된 이메일").type("String"),
                                fieldWithPath("password").description("비밀번호").type("String"),
                                fieldWithPath("nickname").description("닉네임").type("String")
                        ),
                        responseFields(ApiDocHelper.errorResponseFields())
                ));
    }
}
