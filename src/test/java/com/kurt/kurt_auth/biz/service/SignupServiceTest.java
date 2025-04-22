//package com.kurt.kurt_auth.biz.service;
//
//import com.kurt.kurt_auth.biz.dto.SignupDto;
//import com.kurt.kurt_auth.biz.entity.UserMng;
//import com.kurt.kurt_auth.biz.entity.UserMngRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.ArgumentMatchers.reflectsPackage;
//import static org.mockito.Mockito.verify;
//
//@SpringBootTest
//public class SignupServiceTest {
//
//    @Autowired
//    SignupService signupService;
//
//    @MockBean
//    RedisTemplate<String, String> redisTemplate;
//
//    @MockBean
//    JavaMailSender javaMailSender;
//
//    @MockBean
//    UserMngRepository userMngRepository;
//
//    @Test
//    void sendVerifyEmailCode() {
//        String email = "example@example.com";
//        signupService.sendVerifyEmailCode(email);
//
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(redisTemplate.opsForValue()).set(captor.capture(), captor.capture(), eq(Duration.ofSeconds(180)));
//
//        String key = captor.getAllValues().get(0);
//        String code = captor.getAllValues().get(1);
//
//        assertEquals("emailAuth:" + email, key);
//
//        ArgumentCaptor<SimpleMailMessage> captorMessage = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        verify(javaMailSender).send(captorMessage.capture());
//
//        SimpleMailMessage message = captorMessage.getValue();
//
//        assertEquals(email, message.getTo()[0]);
//        assertEquals("이메일 인증번호", message.getSubject());
//        assertEquals("인증번호는 " + code + " 입니다. 3분 이내에 입력해주세요.", message.getText());
//    }
//}