package com.kurt.kurt_auth.biz.controller;

import com.kurt.kurt_auth.biz.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friend/list")
    public ResponseEntity<?> findFriendList() {

        return ResponseEntity.ok(friendService.findFriendList());
    }
}
