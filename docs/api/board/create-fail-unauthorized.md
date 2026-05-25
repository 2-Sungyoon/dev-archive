# Create Fail Unauthorized

## HTTP Request

```http
POST /api/boards HTTP/1.1
Content-Type: application/json
Content-Length: 37
Host: localhost:8080

{"title":"제목","content":"내용"}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+title+` |
| `+String+` |
| 게시글 제목 |
| `+content+` |
| `+String+` |
| 게시글 내용 |

## HTTP Response

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json
Content-Length: 106

{"code":"UNAUTHORIZED","message":"로그인이 필요합니다.","timestamp":"2026-05-25T23:50:52.794583"}
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
$ curl 'http://localhost:8080/api/boards' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"title":"제목","content":"내용"}'
```

