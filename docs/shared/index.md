# Frontend Guide

백엔드 API를 사용하는 프론트엔드 개발자를 위한 가이드입니다.

## 공통 사항

### Base URL

```
개발: http://localhost:8080
스테이징: https://api-staging.archive.com
프로덕션: https://api.archive.com
```

### 인증

모든 인증이 필요한 API는 `Authorization` 헤더에 Bearer 토큰을 포함해야 합니다.

```
Authorization: Bearer <access-token>
```

### 공통 에러 응답 형식

```json
{
  "code": "ERROR_CODE",
  "message": "사람이 읽을 수 있는 메시지",
  "timestamp": "2025-01-15T10:30:00"
}
```

## 주의사항

1. **토큰 갱신**: accessToken 만료 전에 refreshToken으로 갱신하세요.
2. **에러 핸들링**: HTTP 상태 코드와 `code` 필드를 함께 확인하세요.
3. **X-Member-Id**: 이 헤더는 Gateway에서 주입됩니다. 프론트엔드에서 직접 설정하지 마세요.

## 관련 문서

- [에러 코드 목록](./error-codes.md)
- [API Reference](/api/)
