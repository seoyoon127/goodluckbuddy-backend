package com.goodluck_buddy.domain.reply.converter;

import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.domain.reply.entity.Reply;

public class ReplyConverter {

    public static ReplyResDto.Reply toReplyRes(
            Reply reply,
            String writerName
    ) {
        return ReplyResDto.Reply.builder()
                .replyId(reply.getId())
                .likeCount(reply.getLikeCount())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .writerName(writerName)
                .build();
    }
}
