package com.goodluck_buddy.domain.letter.dto;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.LetterDesign;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class LetterResDto {
    @Getter
    @Builder
    public static class Letter {
        private Long letterId;
        private String writerName;
        private String title;
        private String content;
        private Long likeCount;
        private LocalDate createdAt;
        private Category category;
    }

    @Getter
    @Builder
    public static class RecommendLetter {
        private String phrase;
        private List<LetterResDto.Letter> letters;
    }

    @Getter
    @Builder
    public static class LetterDetail {
        private Long letterId;
        private String writerName;
        private String title;
        private String content;
        private LetterDesign letterDesign;
        private LocalDate createdAt;
        private Long likeCount;
        private Category category;
        private List<String> infos;
        private boolean mine;
        private boolean like;
    }
}
