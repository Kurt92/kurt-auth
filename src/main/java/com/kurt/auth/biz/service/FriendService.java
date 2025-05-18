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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserMappingRepository userMappingRepository;
    private final UserMngRepository userMngRepository;
    private final FriendQueryDslRepository friendQueryDslRepository;


    public List<FriendDto.Response.FriendList> findFriendList(Long userId) {

        return friendQueryDslRepository.findFriendList(userId);
    }

    public List<FriendDto.Response.FreindRequestList> findFriendRequestList(Long userId) {

        return friendQueryDslRepository.findFriendRequestList(userId);
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

        // 정보조회
        UserMng userMng = userMngRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        UserMng targetUserMng = userMngRepository.findById(targetId)
                .orElseThrow(()-> new RuntimeException("요청자 정보가 없습니다."));

        // 친구 PENDING 상태로 저장
        UserMapping result = UserMapping.builder()
                .userMng(userMng)
                .targetId(targetUserMng)
                .accountId(userMng.getAccountId())
                .status(FriendStatus.PENDING)
                .build();

        userMappingRepository.save(result);
    }

    @Transactional
    public void requestStatusSet(Long userMappingId, FriendStatus status) {

        // 요청 조회
        UserMapping userMapping = userMappingRepository.findById(userMappingId)
                .orElseThrow(()-> new RuntimeException("친구신청이 없습니다."));

        // 친구 상태 수정
       userMapping.changeStatus(status);
    }
}
