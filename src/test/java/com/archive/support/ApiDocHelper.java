package com.archive.support;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

/**
 * REST Docs 테스트에서 반복되는 필드 정의를 헬퍼로 제공
 */
public class ApiDocHelper {

    // === 공통 에러 응답 필드 ===
    public static FieldDescriptor[] errorResponseFields() {
        return new FieldDescriptor[]{
                fieldWithPath("code").type(JsonFieldType.STRING).description("에러 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("에러 발생 시간 (ISO 8601)")
        };
    }

    // === Auth 관련 상수 ===
    public static final String LOGIN_FRONTEND_NOTE = """
            - accessToken은 Authorization 헤더에 Bearer 접두사와 함께 전달
            - refreshToken은 httpOnly 쿠키 또는 안전한 저장소에 보관
            - expiresIn(초) 기준으로 토큰 갱신 타이머 설정 권장
            """;

    public static final String LOGIN_TRADEOFF = """
            - 토큰 만료 시간을 짧게(1시간) 설정하여 보안성을 높였으나, refresh 빈도 증가
            - refresh token rotation 미적용 상태 → 추후 보안 강화 필요
            """;

    public static final String LOGIN_SIDE_EFFECT = """
            - 로그인 실패 5회 초과 시 계정 잠금 (현재 미구현, 추후 적용 예정)
            """;

    public static final String SIGNUP_FRONTEND_NOTE = """
            - 회원가입 성공 후 자동 로그인 처리하지 않음 → 별도 로그인 호출 필요
            - 이메일 중복 체크는 별도 API 호출 없이 signup 응답의 에러로 처리
            """;

    public static final String SIGNUP_TRADEOFF = """
            - 이메일 인증 없이 즉시 가입 처리 → 빠른 온보딩 vs 스팸 계정 위험
            """;

    public static final String SIGNUP_SIDE_EFFECT = """
            - 가입 시 웰컴 이메일 발송 (비동기, 실패해도 가입은 성공)
            """;

    public static final String MEMBER_ME_FRONTEND_NOTE = """
            - X-Member-Id 헤더는 인증 미들웨어가 토큰에서 추출하여 주입
            - 프론트엔드에서 직접 설정하지 말 것 (Gateway/BFF 레이어에서 주입)
            """;

    public static final String MEMBER_ME_TRADEOFF = """
            - 헤더 기반 식별 → 간단하지만 헤더 조작에 취약 (내부망 전용 설계)
            """;

    public static final String MEMBER_ME_SIDE_EFFECT = """
            - 없음 (조회 전용 API)
            """;
}
