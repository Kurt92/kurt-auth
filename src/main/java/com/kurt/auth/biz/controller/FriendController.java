package com.kurt.auth.biz.controller;

import com.kurt.auth.biz.constant.FriendStatus;
import com.kurt.auth.biz.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    // 친구 목록 조회
    @GetMapping("/friend-list")
    public ResponseEntity<?> findFriendList(@RequestParam Long userId) {

        return ResponseEntity.ok(friendService.findFriendList(userId));
    }

    // 친구 요청 목록 조회
    @GetMapping("/friend-req")
    public ResponseEntity<?> findFriendRequestList(@RequestParam Long userId) {

        return ResponseEntity.ok(friendService.findFriendRequestList(userId));
    }

    // 유저 목록 조회
    @GetMapping("/user-list")
    public ResponseEntity<?> findUserList(@RequestParam String targetNm, @RequestParam Long userId) {

        return ResponseEntity.ok(friendService.findUserList(targetNm, userId));
    }

    // 친구 요청
    @GetMapping("/request-friend")
    public ResponseEntity<?> requestFriend(@RequestParam Long targetId, @RequestParam Long userId) {
        friendService.requestFriend(targetId, userId);
        return ResponseEntity.ok("request success");
    }

    // 친구 요청 수락
    @GetMapping("/request-status")
    public ResponseEntity<?> requestAccept(@RequestParam Long userMappingId, @RequestParam FriendStatus status) {
        friendService.requestStatusSet(userMappingId, status);
        return ResponseEntity.ok("request accept success");
    }

}
