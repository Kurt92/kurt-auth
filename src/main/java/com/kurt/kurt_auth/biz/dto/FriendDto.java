package com.kurt.kurt_auth.biz.dto;

import lombok.*;

public class FriendDto {


    public static class Response {


        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class FriendList {

            private Long userId;
            private String userNm;
            private Long targetId;
            private String targetNm;


        }

    }
}
