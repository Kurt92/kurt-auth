package com.kurt.kurt_auth.biz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_mapping",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_id"})
    }
)
public class UserMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mapping_id")
    @Comment("유저 맵핑 PK")
    private Long userMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserMng userMng;

    @Column(name = "account_id")
    @Comment("계정 ID")
    private String accountId;

    @Column(name = "target_id")
    @Comment("타겟 ID")
    private Long targetId;

}
