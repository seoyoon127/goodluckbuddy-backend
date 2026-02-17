package com.goodluck_buddy.domain.auth.exception.code;

import com.goodluck_buddy.global.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST,
            "AUTH400_1",
            "잘못된 소셜 타입입니다."),
    DUPLICATE_SOCIAL(HttpStatus.BAD_REQUEST,
            "User400_1",
            "잘못된 소셜 타입입니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,
            "TOKEN_404",
            "해당 토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,
            "TOKEN_400",
            "이미 만료된 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
