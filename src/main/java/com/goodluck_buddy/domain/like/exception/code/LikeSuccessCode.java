package com.goodluck_buddy.domain.like.exception.code;

import com.goodluck_buddy.global.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeSuccessCode implements BaseSuccessCode {
    LIKE_SAVE_OK(HttpStatus.OK,
            "LIKE200_1",
            "좋아요가 저장되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
