package com.goodluck_buddy.domain.reply.converter;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.reply.dto.ReplyReqDto;
import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.domain.user.entity.User;

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

    public static Reply toReply(
            ReplyReqDto.Reply dto,
            User user,
            Letter letter
    ) {
        return Reply.builder()
                .content(dto.getContent())
                .user(user)
                .letter(letter)
                .likeCount(0L)
                .build();
    }

    public static ReplyResDto.ReplyPreview toReplyPreviewRes(
            Reply reply,
            String writerName
    ) {
        return ReplyResDto.ReplyPreview.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .letterParentCategory(reply.getLetter().getCategories().getParentCategory())
                .letterId(reply.getLetter().getId())
                .createdAt(reply.getCreatedAt())
                .writerName(writerName)
                .likeCount(reply.getLikeCount())
                .letterTitle(reply.getLetter().getTitle())
                .build();
    }
}
