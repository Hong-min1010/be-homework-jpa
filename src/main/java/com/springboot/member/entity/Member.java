package com.springboot.member.entity;

import com.springboot.order.entity.Order;
import com.springboot.stamp.entity.Stamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    // 추가된 부분
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    // 수정 해야함 add X
    public void setOrder(Order order) {
        orders.add(order);

        if (order.getMember() != this) {
            order.setMember(this);
        };
    }

    // StampCount와 Mapping
    @OneToOne
    private Stamp stamp;

        // stampCount에 Member가 없다면 setMember
//    public void setStampCount(StampCount stampCount) {
//        if (stamp.getMember() != this) {
//            stamp.setMember(this);
//        }
//    }

    // 추가 된 부분
    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
           this.status = status;
        }
    }

//    public void addOrder(Order order) {
//
//        if (order.getMember() != this) {
//            order.setMember(this);
//        }
//
//    }
}
