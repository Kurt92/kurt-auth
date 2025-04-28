package com.kurt.auth.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class LoginDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request {
        private String id;
        private String password;
    }
}
