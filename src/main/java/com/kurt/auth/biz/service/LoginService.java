package com.kurt.auth.biz.service;

import com.kurt.auth.biz.dto.LoginDto;
import com.kurt.auth.biz.entity.UserMng;
import com.kurt.auth.biz.entity.UserMngRepository;
import com.kurt.auth.framework.core.cookie.CookieUtil;
import com.kurt.auth.framework.core.cookie.JwtTokenEnum;
import com.kurt.auth.framework.core.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserMngRepository userMngRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void login(LoginDto.Request loginDTO, HttpServletResponse res) {


        Optional<UserMng> userMngOpt = userMngRepository.findByAccountIdAndAccountPass(loginDTO.getId(), loginDTO.getPassword());
        UserMng userMng = userMngOpt.orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 틀렸습니다."));

        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(), jwtUtil.generateAccessToken(userMng), JwtTokenEnum.acc.getExpiredTime()));
        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.ref.getName(), jwtUtil.generateRefreshToken(userMng), JwtTokenEnum.ref.getExpiredTime()));
    }

    public void autoLogin(String refreshToken, HttpServletResponse res) {

        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(), jwtUtil.reGenerateAccessToken(refreshToken), JwtTokenEnum.acc.getExpiredTime()));
        System.out.println("header cookie set 추가");
        System.out.println(res.getHeader("Set-Cookie"));
    }

    public void logout(String refreshToken, HttpServletResponse res) {

        String accountId = jwtUtil.getUsername(refreshToken);
        redisTemplate.delete("refreshToken : " + accountId);

        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(),"",0L));
        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.ref.getName(),"",0L));


    }
}
