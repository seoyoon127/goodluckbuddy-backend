package com.goodluck_buddy.domain.like.repository;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.like.entity.Like;
import com.goodluck_buddy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndLetter(User user, Letter letter);

    void deleteByUserAndLetter(User user, Letter letter);
}
