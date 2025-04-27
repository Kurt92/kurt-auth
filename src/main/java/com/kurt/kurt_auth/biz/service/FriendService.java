package com.kurt.kurt_auth.biz.service;

import com.kurt.kurt_auth.biz.dto.FriendDto;
import com.kurt.kurt_auth.biz.repository.FriendQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendQueryDslRepository friendQueryDslRepository;

    public List<FriendDto.Response.FriendList> findFriendList(Long userId) {

        return friendQueryDslRepository.findFriendList(userId);
    }
}
