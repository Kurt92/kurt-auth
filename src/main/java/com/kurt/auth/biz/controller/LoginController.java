package com.kurt.auth.biz.controller;

import com.kurt.auth.biz.dto.LoginDto;
import com.kurt.auth.biz.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto.Request loginDTO, HttpServletResponse res) {

        loginService.login(loginDTO, res);

        return ResponseEntity.ok("Login Successful");
    }

    @GetMapping("/auto-login")
    public ResponseEntity<?> autoLogin(@CookieValue("kurt_refresh_token") String refreshToken, HttpServletResponse res) {

        loginService.autoLogin(refreshToken, res);

        return ResponseEntity.ok("Login Successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("kurt_refresh_token") String refreshToken, HttpServletResponse res) {

        loginService.logout(refreshToken ,res);

        return ResponseEntity.ok("Logout Successful");
    }











}
