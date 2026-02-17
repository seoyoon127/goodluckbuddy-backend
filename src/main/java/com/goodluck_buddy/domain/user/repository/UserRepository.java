package com.goodluck_buddy.domain.user.repository;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.enums.Gender;
import com.goodluck_buddy.domain.user.enums.SocialType;
import com.goodluck_buddy.domain.user.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialTypeAndProviderId(SocialType socialType, String providerId);

    boolean existsUserByNickname(String nickname);

    @Modifying
    @Query("""
               UPDATE User u
               SET u.nickname = :nickname,
                   u.gender = :gender,
                   u.birth = :birth,
                   u.interestCategory = :category
               WHERE u.id = :id
            """)
    void updateProfile(@Param("id") Long id, @Param("nickname") String nickname,
                       @Param("gender") Gender gender, @Param("birth") LocalDate birth, @Param("category") Category category);

    @Modifying
    @Query("""
                        UPDATE User u
                        SET u.status = :status,
                            u.providerId = :providerId
                        WHERE u.id = :id
            """)
    void withdraw(@Param("id") Long id, @Param("status") Status status, @Param("providerId") String providerId);
}
