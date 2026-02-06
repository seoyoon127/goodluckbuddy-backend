package com.goodluck_buddy.domain.like.repository;

import com.goodluck_buddy.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
