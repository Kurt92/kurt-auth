package com.kurt.auth.biz.service;

import com.kurt.auth.biz.constant.FriendStatus;
import com.kurt.auth.biz.dto.FriendDto;
import com.kurt.auth.biz.entity.UserMapping;
import com.kurt.auth.biz.entity.UserMappingRepository;
import com.kurt.auth.biz.entity.UserMng;
import com.kurt.auth.biz.entity.UserMngRepository;
import com.kurt.auth.biz.repository.FriendQueryDslRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserMappingRepository userMappingRepository;
    private final UserMngRepository userMngRepository;
    private final FriendQueryDslRepository friendQueryDslRepository;


    public List<FriendDto.Response.FriendList> findFriendList(Long userId) {

        return friendQueryDslRepository.findFriendList(userId);
    }

    public List<FriendDto.Response.UserList> findUserList(String targetNm, Long userId) {

        return friendQueryDslRepository.findUserList(targetNm, userId);
    }

    @Transactional
    public void requestFriend(Long targetId, Long userId) {
        // 친구 여부 확인
        // 신청자와는 관계없이 친구관계가 있다면 false
        boolean exists = friendQueryDslRepository.existsFriendRelation(targetId, userId);
        if (exists) throw new RuntimeException("이미 등록된 친구입니다."); 

        // 요청자 정보조회
        UserMng userMng = userMngRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        // 친구 PENDING 상태로 저장
        UserMapping mapping = UserMapping.builder()
                .userMng(userMng)
                .targetId(targetId)
                .accountId(userMng.getAccountId())
                .status(FriendStatus.PENDING)
                .build();

        userMappingRepository.save(mapping);
    }
}
