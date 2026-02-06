package com.goodluck_buddy.domain.like.converter;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.like.entity.Like;
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
}
