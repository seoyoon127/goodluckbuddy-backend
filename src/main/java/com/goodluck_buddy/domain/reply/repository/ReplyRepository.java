package com.goodluck_buddy.domain.reply.repository;

import com.goodluck_buddy.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
