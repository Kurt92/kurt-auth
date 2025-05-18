package com.kurt.auth.biz.repository;

import com.kurt.auth.biz.constant.FriendStatus;
import com.kurt.auth.biz.dto.FriendDto;
import com.querydsl.core.types.Projections;
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

        List<FriendDto.Response.FriendList> result = queryFactory
            .select(
                    Projections.fields(
                            FriendDto.Response.FriendList.class,
                            userMapping.userMng.userId,
                            userMapping.accountId,
                            userMapping.targetId.userId.as("targetId"),
                            userMng.userNm.as("targetNm")

                )
            )
            .from(userMapping)
            .innerJoin(userMng).on(userMapping.targetId.userId.eq(userMng.userId))
            .where(
                    userMapping.userMng.userId.eq(userId).or(userMapping.targetId.userId.eq(userId)),
                    userMapping.status.eq(FriendStatus.ACCEPTED)
            )
            .fetch();

        return result;

    }


    public List<FriendDto.Response.UserList> findUserList(String targetNm, Long userId) {


        List<FriendDto.Response.UserList> result = queryFactory
                .select(Projections.fields(
                        FriendDto.Response.UserList.class,
                        userMng.userId,
                        userMng.userNm.as("targetNm"),
                        userMng.userId.as("targetId"),
                        userMapping.userMng.userId.isNotNull().as("isFriend")
                ))
                .from(userMng)
                .leftJoin(userMapping).on(userMapping.userMng.userId.eq(userId)
                        .and(userMapping.targetId.userId.eq(userMng.userId)))
                .where(
                        userMng.userId.ne(userId),
                        targetNm != null ? userMng.userNm.startsWith(targetNm) : null
                )
                .fetch();

        return result;

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
