package com.kurt.auth.biz.service;


import com.kurt.auth.biz.dto.SignupDto;
import com.kurt.auth.biz.entity.UserMng;
import com.kurt.auth.biz.entity.UserMngRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignupService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserMngRepository userMngRepository;



    //회원가입
    public void signup(SignupDto.Request signupDto) throws Exception {

        UserMng userMng = UserMng.builder()
                .accountId(signupDto.getAccountId())
                .accountPass(signupDto.getPassword())
                .email(signupDto.getEmail())
                .userNm(signupDto.getUserNm())
                .build();

        userMngRepository.save(userMng);
    }


    //아이디 중복확인
    public Boolean duplicateCheck(String accountId) {

        return userMngRepository.existsByAccountId(accountId);
    }


    //이메일 검증 코드 전송
    public void sendVerifyEmailCode(String email) {


        String code = genRandomCode();

        // 1. Redis에 저장
        redisTemplate.opsForValue().set("emailAuth:" + email, code, Duration.ofSeconds(180));

        // 2. 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증번호");
        message.setText("인증번호는 " + code + " 입니다. 3분 이내에 입력해주세요.");
        javaMailSender.send(message);
    }

    //이메일 검증 확인
    public void  verifyEmailCode(String email, String code) {

        String savedCode = redisTemplate.opsForValue().get("emailAuth:" + email);

        if (savedCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // 404
        }

        if (!savedCode.equals(code)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); // 401
        }
    }



    // 랜덤으로 숫자 생성
    private String genRandomCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000)); // 6자리 숫자
    }

}
