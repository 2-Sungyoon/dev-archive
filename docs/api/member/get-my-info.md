# Get My Info

## HTTP Request

```http
GET /api/members/me HTTP/1.1
X-Member-Id: 1
Accept: application/json
Host: localhost:8080
```

## Request Headers

| Name | Description |
| --- | --- |
| `+X-Member-Id+` |
| 인증된 회원 ID (Gateway에서 주입) |

## HTTP Response

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 101

{"id":1,"email":"user@archive.com","nickname":"아카이브유저","createdAt":"2025-01-15T10:30:00"}
```

## Response Fields

| Path | Type | Description |
| --- | --- | --- |
| `+id+` |
| `+Number+` |
| 회원 ID |
| `+email+` |
| `+String+` |
| 이메일 |
| `+nickname+` |
| `+String+` |
| 닉네임 |
| `+createdAt+` |
| `+String+` |
| 가입 일시 (ISO 8601) |

## cURL Example

```bash
$ curl 'http://localhost:8080/api/members/me' -i -X GET \
    -H 'X-Member-Id: 1' \
    -H 'Accept: application/json'
```

