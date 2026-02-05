package com.goodluck_buddy.domain.letter.dto;

import com.goodluck_buddy.domain.letter.enums.Category;
import lombok.Builder;
import lombok.Getter;

public class LetterResDto {
    @Getter
    @Builder
    public static class Letter {
        private Long letterId;
        private String writerName;
        private String title;
        private String content;
        private Long likeCount;
        private Category parentCategory;
    }
}
