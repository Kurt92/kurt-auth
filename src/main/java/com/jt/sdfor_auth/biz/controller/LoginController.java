package com.jt.sdfor_auth.biz.controller;

import com.jt.sdfor_auth.biz.dto.LoginDTO;
import com.jt.sdfor_auth.biz.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO.Request loginDTO, HttpServletResponse res) {

        loginService.login(loginDTO, res);

        return ResponseEntity.ok("Login Successful");
    }

    @PostMapping("/auth/auto-login")
    public ResponseEntity<?> autoLogin(@CookieValue("sdFor_refresh_token") String refreshToken, HttpServletResponse res) {

        loginService.autoLogin(refreshToken, res);

        return ResponseEntity.ok("Login Successful");
    }
}
