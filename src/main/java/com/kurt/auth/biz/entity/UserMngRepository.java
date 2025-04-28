package com.kurt.auth.biz.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMngRepository extends JpaRepository<UserMng, Long> {

    UserMng findByAccountIdAndAccountPass(String AccountId, String AccountPass);
    Boolean existsByAccountId(String accountId);
}
