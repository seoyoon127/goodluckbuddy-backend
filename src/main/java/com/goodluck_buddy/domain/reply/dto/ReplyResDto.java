package com.goodluck_buddy.domain.reply.dto;

import com.goodluck_buddy.domain.letter.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReplyResDto {

    @Getter
    @Builder
    public static class Reply {
        private Long replyId;
        private String content;
        private String writerName;
        private LocalDateTime createdAt;
        private Long likeCount;
    }

    @Getter
    @Builder
    public static class ReplyPreview {
        private Long letterId;
        private Long replyId;
        private String letterTitle;
        private String content;
        private String writerName;
        private LocalDateTime createdAt;
        private Long likeCount;
        private Category letterParentCategory;
    }
}
