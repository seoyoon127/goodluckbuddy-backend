package com.goodluck_buddy.domain.like.repository;

import com.goodluck_buddy.domain.like.entity.ReplyLike;
import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    boolean existsByUserAndReply(User user, Reply reply);
}
