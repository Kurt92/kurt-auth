package com.kurt.auth.biz.repository;

import com.kurt.auth.biz.constant.FriendStatus;
import com.kurt.auth.biz.dto.FriendDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kurt.auth.biz.entity.QUserMapping.userMapping;
import static com.kurt.auth.biz.entity.QUserMng.userMng;

@Repository
@RequiredArgsConstructor
public class FriendQueryDslRepository {

    private final JPAQueryFactory queryFactory;


    public List<FriendDto.Response.FriendList> findFriendList(Long userId) {

        // on을 생략한 조인은 연관관계가 맺어져있는 상태면 가능함.
        // 처음써보는데 저렇게 축약 가능하다함, 아래와 같은 조인이였음 원래

        return queryFactory
                .select(
                        Projections.fields(
                                FriendDto.Response.FriendList.class,
                                userMapping.accountId,
                                // 상대방 ID
                                Expressions.cases()
                                        .when(userMapping.userMng.userId.eq(userId))
                                        .then(userMapping.targetId.userId)
                                        .otherwise(userMapping.userMng.userId)
                                        .as("targetId"),
                                // 상대방 이름
                                Expressions.cases()
                                        .when(userMapping.userMng.userId.eq(userId))
                                        .then(userMapping.targetId.userNm)
                                        .otherwise(userMapping.userMng.userNm)
                                        .as("targetNm")
                        )
                )
                .from(userMapping)
//                 .innerJoin(userMng).on(userMng.userId.eq(userMapping.userMng.userId))
//                 .innerJoin(userMng).on(userMng.userId.eq(userMapping.targetId.userId))
                .join(userMapping.userMng)
                .join(userMapping.targetId)
                .where(
                        userMapping.status.eq(FriendStatus.ACCEPTED),
                        userMapping.userMng.userId.eq(userId).or(userMapping.targetId.userId.eq(userId))
                )
                .fetch();
    }


    public List<FriendDto.Response.UserList> findUserList(String targetNm, Long userId) {

        return queryFactory
                .select(
                        Projections.fields(
                                FriendDto.Response.UserList.class,
                                userMng.userId,
                                userMng.userNm.as("targetNm"),
                                userMng.userId.as("targetId")
                        )
                )
                .from(userMng)
                .where(
                        userMng.userId.ne(userId), // 본인 제외
                        targetNm != null ? userMng.userNm.startsWith(targetNm) : null,
                        // 친구관계가 아닌 유저만 남기기
                        JPAExpressions.selectOne()
                                .from(userMapping)
                                .where(
                                        userMapping.status.eq(FriendStatus.ACCEPTED),
                                        (
                                                userMapping.userMng.userId.eq(userId)
                                                        .and(userMapping.targetId.userId.eq(userMng.userId))
                                        ).or(
                                                userMapping.userMng.userId.eq(userMng.userId)
                                                        .and(userMapping.targetId.userId.eq(userId))
                                        )
                                )
                                .notExists()
                )
                .fetch();
    }

    public List<FriendDto.Response.FreindRequestList> findFriendRequestList(Long userId) {

        List<FriendDto.Response.FreindRequestList> result = queryFactory
                .select(
                        Projections.fields(
                                FriendDto.Response.FreindRequestList.class,
                                userMapping.userMappingId,
                                userMapping.targetId.userId.as("targetId"),
                                userMng.userNm.as("targetNm")

                        )
                )
                .from(userMapping)
                .innerJoin(userMng).on(userMapping.userMng.userId.eq(userMng.userId))
                .where(
                        userMapping.targetId.userId.eq(userId),
                        userMapping.status.eq(FriendStatus.PENDING)
                )
                .fetch();

        return result;

    }

    public boolean existsFriendRelation(Long targetId, Long userId) {
        return queryFactory
                .selectOne()
                .from(userMapping)
                .where(
                        userMapping.userMng.userId.eq(userId).and(userMapping.targetId.userId.eq(targetId))
                                .or(userMapping.userMng.userId.eq(targetId).and(userMapping.targetId.userId.eq(userId)))
                )
                .fetchFirst() != null;
    }
}
