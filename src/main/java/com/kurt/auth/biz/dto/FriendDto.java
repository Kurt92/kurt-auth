package com.kurt.auth.biz.dto;

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
            private String accountId;
            private String userNm;
            private Long targetId;
            private String targetNm;


        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class UserList {

            private Long userId;
            private Long targetId;
            private String targetNm;
            private Boolean isAlreadyFriend;


        }

    }
}
