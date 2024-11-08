package com.jt.sdfor_auth.framework.core.cookie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenEnum {

    acc("sdFor_access_token", 15 * 60),   // 15분
    ref("sdFor_refresh_token",7 * 24 * 60 * 60);  // 7일

    private final String name;
    private final int expiredTime;


}
