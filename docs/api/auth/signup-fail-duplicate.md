# Signup Fail Duplicate

## HTTP Request

```http
POST /api/auth/signup HTTP/1.1
Content-Type: application/json
Content-Length: 77
Host: localhost:8080

{"email":"existing@archive.com","password":"password123","nickname":"유저"}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+email+` |
| `+String+` |
| 이미 가입된 이메일 |
| `+password+` |
| `+String+` |
| 비밀번호 |
| `+nickname+` |
| `+String+` |
| 닉네임 |

## HTTP Response

```http
HTTP/1.1 400 Bad Request
Content-Type: application/json
Content-Length: 112

{"code":"BAD_REQUEST","message":"이미 가입된 이메일입니다.","timestamp":"2026-05-25T23:50:52.759697"}
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
$ curl 'http://localhost:8080/api/auth/signup' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"email":"existing@archive.com","password":"password123","nickname":"유저"}'
```

