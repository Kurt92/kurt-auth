package com.jt.sdfor_auth.biz.service;

import com.jt.sdfor_auth.biz.dto.LoginDTO;
import com.jt.sdfor_auth.biz.entity.UserMng;
import com.jt.sdfor_auth.biz.entity.UserMngRepository;
import com.jt.sdfor_auth.framework.core.cookie.CookieUtil;
import com.jt.sdfor_auth.framework.core.cookie.JwtTokenEnum;
import com.jt.sdfor_auth.framework.core.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserMngRepository userMngRepository;

    public void login(LoginDTO.Request loginDTO, HttpServletResponse res) {

        UserMng userMng = userMngRepository.findByAccountIdAndAccountPass(loginDTO.getId(), loginDTO.getPassword());

        res.addCookie(cookieUtil.generateTokenCookie(JwtTokenEnum.acc.getName(), jwtUtil.generateAccessToken(userMng)));
        res.addCookie(cookieUtil.generateTokenCookie(JwtTokenEnum.ref.getName(), jwtUtil.generateRefreshToken(userMng)));
    }
}
