package com.goodluck_buddy.domain.reply.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReplyReqDto {

    @Getter
    public static class Reply {
        @NotNull
        private String content;
    }
}
