package com.goodluck_buddy.domain.user.entity;

import com.goodluck_buddy.domain.user.enums.Category;
import com.goodluck_buddy.domain.user.enums.Gender;
import com.goodluck_buddy.domain.user.enums.SocialType;
import com.goodluck_buddy.domain.user.enums.Status;
import com.goodluck_buddy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_id", unique = true, nullable = false)
    private String providerId;

    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "interest_category")
    private Category interestCategory;

    @Column(name = "status")
    private Status status;
}
