# Login Fail Credentials

## HTTP Request

```http
POST /api/auth/login HTTP/1.1
Content-Type: application/json
Content-Length: 56
Host: localhost:8080

{"email":"user@archive.com","password":"wrong-password"}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+email+` |
| `+String+` |
| 로그인 이메일 |
| `+password+` |
| `+String+` |
| 비밀번호 (잘못된 값) |

## HTTP Response

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json
Content-Length: 136

{"code":"UNAUTHORIZED","message":"이메일 또는 비밀번호가 올바르지 않습니다.","timestamp":"2026-05-25T23:50:52.656799"}
```

## Response Fields

| Path | Type | Description |
| --- | --- | --- |
| `+code+` |
| `+String+` |
| 에러 코드 |
| `+message+` |
| `+String+` |
| 에러 메시지 |
| `+timestamp+` |
| `+String+` |
| 에러 발생 시간 (ISO 8601) |

## cURL Example

```bash
$ curl 'http://localhost:8080/api/auth/login' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"email":"user@archive.com","password":"wrong-password"}'
```

