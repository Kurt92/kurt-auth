package com.kurt.auth.biz.controller;

import com.kurt.auth.biz.dto.SignupDto;
import com.kurt.auth.biz.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignupDto.Request signupDto) throws Exception {

        signupService.signup(signupDto);

        return ResponseEntity.ok("signup success");
    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<?> duplicateCheck(@RequestParam String accountId) {

        return ResponseEntity.ok(signupService.duplicateCheck(accountId));
    }

    @GetMapping("/email-verify-code-send")
    public ResponseEntity<?> EmailVerifyCod(@RequestParam String email) {

        signupService.sendVerifyEmailCode(email);

        return ResponseEntity.ok("email verify code send success");
    }

    @GetMapping("/email-verify-code-check")
    public ResponseEntity<?> EmailVerifyCodeCheck(@RequestParam String email, @RequestParam String code) {

        signupService.verifyEmailCode(email, code);

        return ResponseEntity.ok("email verify code");
    }

}
