# Signup Success

## HTTP Request

```http
POST /api/auth/signup HTTP/1.1
Content-Type: application/json
Content-Length: 75
Host: localhost:8080

{"email":"new@archive.com","password":"password123","nickname":"뉴유저"}
```

## Request Fields

| Path | Type | Description |
| --- | --- | --- |
| `+email+` |
| `+String+` |
| 가입 이메일 |
| `+password+` |
| `+String+` |
| 비밀번호 (8~20자) |
| `+nickname+` |
| `+String+` |
| 닉네임 (2~10자) |

## HTTP Response

```http
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 63

{"memberId":1,"email":"new@archive.com","nickname":"뉴유저"}
```

## Response Fields

| Path | Type | Description |
| --- | --- | --- |
| `+memberId+` |
| `+Number+` |
| 생성된 회원 ID |
| `+email+` |
| `+String+` |
| 가입된 이메일 |
| `+nickname+` |
| `+String+` |
| 닉네임 |

## cURL Example

```bash
$ curl 'http://localhost:8080/api/auth/signup' -i -X POST \
    -H 'Content-Type: application/json' \
    -d '{"email":"new@archive.com","password":"password123","nickname":"뉴유저"}'
```

