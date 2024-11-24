package com.kurt.kurt_auth.biz.controller;

import com.kurt.kurt_auth.biz.dto.SignupDto;
import com.kurt.kurt_auth.biz.service.SignupService;
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

//        signupService.duplicateCheck(accountId);

        return ResponseEntity.ok(signupService.duplicateCheck(accountId));
    }

}
