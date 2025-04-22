package com.kurt.kurt_auth.biz.repository;

import com.kurt.kurt_auth.biz.dto.FriendDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kurt.kurt_auth.biz.entity.QUserMapping.userMapping;
import static com.kurt.kurt_auth.biz.entity.QUserMng.userMng;

@Repository
@RequiredArgsConstructor
public class FriendQueryDslRepository {

    private final JPAQueryFactory queryFactory;


    public List<FriendDto.Response.FriendList> findFriendList() {

        List<FriendDto.Response.FriendList> result = queryFactory
            .select(
                    Projections.fields(
                            FriendDto.Response.FriendList.class,
                            userMapping.userMng.userId,
                            userMapping.accountId,
                            userMapping.targetId

                )
            )
            .from(userMapping)
            .innerJoin(userMng).on(userMapping.targetId.eq(userMng.userId))
            .fetch();

        return result;

    }


}
