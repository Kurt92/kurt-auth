package com.kurt.auth.biz.controller;

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



    @GetMapping("/friend-list")
    public ResponseEntity<?> findFriendList(@RequestParam Long userId) {

        return ResponseEntity.ok(friendService.findFriendList(userId));
    }

    @GetMapping("/user-list")
    public ResponseEntity<?> findUserList(@RequestParam String targetNm, @RequestParam Long userId) {

        return ResponseEntity.ok(friendService.findUserList(targetNm, userId));
    }

    @GetMapping("/request-friend")
    public ResponseEntity<?> requestFriend(@RequestParam Long targetId, @RequestParam Long userId) {
        friendService.requestFriend(targetId, userId);
        return ResponseEntity.ok("request success");
    }

}
