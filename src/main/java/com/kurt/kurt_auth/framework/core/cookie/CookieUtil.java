package com.kurt.kurt_auth.framework.core.cookie;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieUtil {


    public Cookie generateTokenCookie(String tokenNm, String token) {

        Cookie tokenCookie = new Cookie(tokenNm, token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(15 * 60); // 15분

        return tokenCookie;
    }
}
