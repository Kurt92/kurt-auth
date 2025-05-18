package com.kurt.auth.biz.entity;


import com.kurt.auth.biz.constant.FriendStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    @Comment("타겟 ID")
    private UserMng targetId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Comment("친구 상태: PENDING, ACCEPTED, REJECTED")
    private FriendStatus status;


    public void changeStatus(FriendStatus status) {
        this.status = status;
    }

}
