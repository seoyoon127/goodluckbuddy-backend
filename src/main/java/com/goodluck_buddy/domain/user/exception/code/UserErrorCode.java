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
            "해당 사용자를 찾지 못했습니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST,
            "USER400_1",
            "이미 존재하는 닉네임입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
