package com.goodluck_buddy.domain.reply.exception.code;

import com.goodluck_buddy.global.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyErrorCode implements BaseErrorCode {
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REPLY404",
            "해당 답글을 찾을 수 없습니다."),
    INVALID_WRITER(HttpStatus.BAD_REQUEST,
            "WRITER400",
            "작성자만 접근할 수 있는 기능입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
