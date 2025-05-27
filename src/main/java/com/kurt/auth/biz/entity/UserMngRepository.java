package com.kurt.auth.biz.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMngRepository extends JpaRepository<UserMng, Long> {

    Optional<UserMng> findByAccountIdAndAccountPass(String AccountId, String AccountPass);
    Boolean existsByAccountId(String accountId);
}
