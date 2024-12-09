package com.kurt.kurt_auth.biz.controller;

import com.kurt.kurt_auth.biz.dto.LoginDto;
import com.kurt.kurt_auth.biz.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto.Request loginDTO, HttpServletResponse res, HttpServletRequest req) {

        loginService.login(loginDTO, res, req);

        return ResponseEntity.ok("Login Successful");
    }

    @GetMapping("/auth/auto-login")
    public ResponseEntity<?> autoLogin(@CookieValue("kurt_refresh_token") String refreshToken, HttpServletResponse res, HttpServletRequest req) {

        loginService.autoLogin(refreshToken, res, req);

        return ResponseEntity.ok("Login Successful");
    }











}
