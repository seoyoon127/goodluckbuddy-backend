package com.goodluck_buddy.domain.user.repository;

import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialTypeAndProviderId(SocialType socialType, String providerId);
}
