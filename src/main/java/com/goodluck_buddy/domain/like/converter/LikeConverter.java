package com.goodluck_buddy.domain.like.converter;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.like.dto.LikeResDto;
import com.goodluck_buddy.domain.like.entity.Like;
import com.goodluck_buddy.domain.like.entity.ReplyLike;
import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.domain.user.entity.User;

public class LikeConverter {
    public static Like toLike(
            Letter letter,
            User user
    ) {
        return Like.builder()
                .letter(letter)
                .user(user)
                .likes(true)
                .build();
    }

    public static ReplyLike toReplyLike(
            Reply reply,
            User user
    ) {
        return ReplyLike.builder()
                .reply(reply)
                .user(user)
                .likes(true)
                .build();
    }

    public static LikeResDto.Likes toLikeRes(
            Long likeCount,
            boolean likes
    ) {
        return LikeResDto.Likes.builder()
                .likeCount(likeCount)
                .likes(likes)
                .build();
    }
}
