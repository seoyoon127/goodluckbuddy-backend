package com.goodluck_buddy.domain.user.exception.code;

import com.goodluck_buddy.global.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "USER404",
            "해당 사용자를 찾지 못했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
