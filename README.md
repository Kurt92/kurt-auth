# kurt_auth

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
sudo systemctl start kurt_auth (서비스 실행)<br>
sudo journalctl -u kurt_auth -f (서비스 콘솔)



### redis 
sudo -u redis redis-server /etc/redis/redis.conf <br>
레디스 서비스 등록이 안되서 수동으로 키고있음 <br>
추후 수정 요망 (aws 엘라스틱 캐시)