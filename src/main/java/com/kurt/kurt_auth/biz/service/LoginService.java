package com.kurt.kurt_auth.biz.service;

import com.kurt.kurt_auth.biz.dto.LoginDto;
import com.kurt.kurt_auth.biz.entity.UserMng;
import com.kurt.kurt_auth.biz.entity.UserMngRepository;
import com.kurt.kurt_auth.framework.core.cookie.CookieUtil;
import com.kurt.kurt_auth.framework.core.cookie.JwtTokenEnum;
import com.kurt.kurt_auth.framework.core.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserMngRepository userMngRepository;

    public void login(LoginDto.Request loginDTO, HttpServletResponse res, HttpServletRequest req) {

        UserMng userMng = userMngRepository.findByAccountIdAndAccountPass(loginDTO.getId(), loginDTO.getPassword());
        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(), jwtUtil.generateAccessToken(userMng), JwtTokenEnum.acc.getExpiredTime(), req));
        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.ref.getName(), jwtUtil.generateRefreshToken(userMng), JwtTokenEnum.ref.getExpiredTime(), req));
    }

    public void autoLogin(String refreshToken, HttpServletResponse res, HttpServletRequest req) {

        res.addHeader("Set-Cookie", cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(), jwtUtil.reGenerateAccessToken(refreshToken), JwtTokenEnum.acc.getExpiredTime(), req));
        System.out.println("header cookie set 추가");
        System.out.println(res.getHeader("Set-Cookie"));
    }
}
