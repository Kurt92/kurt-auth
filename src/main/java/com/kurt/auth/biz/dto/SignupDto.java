package com.kurt.auth.biz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SignupDto {

    @Data
    public static class Request {

        @NotNull
        private String accountId;
        private String password;
        private String email;
        private String userNm;
    }
}
