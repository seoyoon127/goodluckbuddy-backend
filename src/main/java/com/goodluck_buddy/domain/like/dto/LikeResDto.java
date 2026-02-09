package com.goodluck_buddy.domain.like.dto;

import lombok.Builder;
import lombok.Getter;

public class LikeResDto {

    @Getter
    @Builder
    public static class Likes {
        public Long likeCount;
        public boolean likes;
    }
}
