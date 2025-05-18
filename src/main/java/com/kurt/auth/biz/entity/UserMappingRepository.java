package com.kurt.auth.biz.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingRepository extends JpaRepository<UserMapping, Long> {


}
