# kurt-auth

JWT 발급을 위한 auth 서버

## Stack
- Sping boot 3
- Jpa
- QueryDsl
- Spring Security



## Git Commit Rule
feat / fix / refactor

feat : 신규개발  
fix : 버그수정  
refactor : 리펙토링

### Messege Ex

feat : board 비즈니스 로직 작성

#작업내용 : board service 구현
  
<br>
<br>
<br>
<br>

-----------------------------------

### EC2 서비스 등록완료
서버 내려가도 재시작 하도록 설정해놨음
<br>
sudo systemctl start kurt-auth (서비스 실행)<br>
sudo journalctl -u kurt-auth -f (서비스 콘솔)



### redis 
sudo -u redis redis-server /etc/redis/redis.conf <br>
레디스 서비스 등록이 안되서 수동으로 키고있음 <br>
추후 수정 요망 (aws 엘라스틱 캐시)

### 2025-04-23 
GitGuardian가 SMTP credentials를 감지했다는 경고가 날아옴.<br>
앱 비밀번호 삭제하였고, 재생성 함.<br><br>
.env.example 을 만들어 깃에 푸쉬<br>
개발시 .env.example를 참고하여 작성하거나, .env 따로 공유 <br>
운영은 docker-compose로 관리


.env.example 예시
```
JWT_SECRET_KEY=

# Redis 설정
REDIS_HOST=localhost
REDIS_PORT=6379
# REDIS_PASSWORD=

# SMTP 이메일 발송 설정 (Gmail 예시)
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=
SMTP_PASSWORD=
SMTP_STARTTLS_ENABLE=true
SMTP_AUTH=true
```