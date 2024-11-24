package com.kurt.kurt_auth.biz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SignupDto {

    @Data
    public static class Request {

        @NotNull
        String accountId;
        String password;
        String email;
    }
}
