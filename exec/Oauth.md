## 구글 클라우드 사용자 인증 정보 등록
1. 구글 콘솔 -> 프로젝트 생성
2. 사용자 인증 -> OAuth 2.0 클라이언트 ID 생성
3. 승인된 리디렉션 URI 등록 (각 개발 환경에 맞도록 허용 설정)
```
http://localhost:8080/login/oauth2/code/google
http://localhost:8080/dev/login/oauth2/code/google
http://localhost:8080/api/login/oauth2/code/google

http://j9a204.p.ssafy.io/login/oauth2/code/google
http://j9a204.p.ssafy.io/dev/login/oauth2/code/google
http://j9a204.p.ssafy.io/api/login/oauth2/code/google
`
