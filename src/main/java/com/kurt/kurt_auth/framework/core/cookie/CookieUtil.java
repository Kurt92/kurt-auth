package com.kurt.kurt_auth.framework.core.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieUtil {

//    public String generateTokenCookie(String tokenNm, String token, int expiredTime) {
//
//        Cookie tokenCookie = new Cookie(tokenNm, token);
//        tokenCookie.setHttpOnly(true);
//        tokenCookie.setSecure(false);
//        tokenCookie.setPath("/");
//        tokenCookie.setMaxAge(expiredTime); // 15분
//
//        return tokenCookie.toString();
//    }

    public String generateTokenCookie(String tokenNm, String token, int expiredTime, HttpServletRequest request) {

        String domain = request.getServerName().contains("localhost") ? null : "loaon.store";

        return ResponseCookie.from(tokenNm, token)
                .httpOnly(true)
                .secure(true) // HTTPS 환경에서는 true로 설정
                .domain(domain) // 도메인 설정 (로컬은 null)
                .path("/")
                .maxAge(expiredTime)
                .sameSite("None")
                .build()
                .toString();
    }
}
