# Login Success

## HTTP Request

```http
POST /api/auth/login HTTP/1.1
Content-Type: application/json
Content-Length: 53
Host: localhost:8080

{"email":"user@archive.com","password":"password123"}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+email+` |
| `+String+` |
| 로그인 이메일 |
| `+password+` |
| `+String+` |
| 비밀번호 |

## HTTP Response

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 130

{"accessToken":"eyJhbGciOiJIUzI1NiJ9.mock-access-token","refreshToken":"eyJhbGciOiJIUzI1NiJ9.mock-refresh-token","expiresIn":3600}
```

## Response Fields

| Path | Type | Description |
| --- | --- | --- |
| `+accessToken+` |
| `+String+` |
| 액세스 토큰 (JWT) |
| `+refreshToken+` |
| `+String+` |
| 리프레시 토큰 |
| `+expiresIn+` |
| `+Number+` |
| 토큰 만료 시간 (초) |

## cURL Example

```bash
$ curl 'http://localhost:8080/api/auth/login' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"email":"user@archive.com","password":"password123"}'
```

