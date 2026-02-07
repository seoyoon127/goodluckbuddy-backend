package com.goodluck_buddy.domain.reply.exception.code;

import com.goodluck_buddy.global.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplySuccessCode implements BaseSuccessCode {

    REPLY_GET_OK(HttpStatus.OK,
            "REPLY200_1",
            "답글 조회에 성공했습니다."),
    REPLY_SAVE_OK(HttpStatus.OK,
            "REPLY200_2",
            "답글 저장에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
