package com.kurt.kurt_auth.framework.core.cookie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenEnum {

    acc("kurt_access_token", 15 * 60 * 1000L),   // 15분
    ref("kurt_refresh_token",7 * 24 * 60 * 60 * 1000L);  // 7일

    private final String name;
    private final Long expiredTime;


}
