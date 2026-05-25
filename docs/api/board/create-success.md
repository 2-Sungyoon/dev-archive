# Create Success

## HTTP Request

```http
POST /api/boards HTTP/1.1
Content-Type: application/json
X-Member-Id: 1
Content-Length: 71
Host: localhost:8080

{"title":"첫 번째 게시글","content":"게시글 내용입니다."}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+title+` |
| `+String+` |
| 게시글 제목 (최대 100자) |
| `+content+` |
| `+String+` |
| 게시글 내용 |

## Request Headers

| Name | Description |
| --- | --- |
| `+X-Member-Id+` |
| 인증된 회원 ID (Gateway에서 주입) |

## HTTP Response

```http
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 130

{"boardId":1,"title":"첫 번째 게시글","content":"게시글 내용입니다.","authorId":1,"createdAt":"2025-01-15T10:30:00"}
```

## Response Fields

| Path | Type | Description |
| --- | --- | --- |
| `+boardId+` |
| `+Number+` |
| 생성된 게시글 ID |
| `+title+` |
| `+String+` |
| 게시글 제목 |
| `+content+` |
| `+String+` |
| 게시글 내용 |
| `+authorId+` |
| `+Number+` |
| 작성자 회원 ID |
| `+createdAt+` |
| `+String+` |
| 생성 시간 (ISO 8601) |

## cURL Example

```bash
$ curl 'http://localhost:8080/api/boards' -i -X POST \
    -H 'Content-Type: application/json' \
    -H 'X-Member-Id: 1' \
    -d '{"title":"첫 번째 게시글","content":"게시글 내용입니다."}'
```

