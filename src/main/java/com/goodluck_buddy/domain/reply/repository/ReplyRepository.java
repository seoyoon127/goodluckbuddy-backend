package com.goodluck_buddy.domain.reply.repository;

import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByUser(User user);
}
