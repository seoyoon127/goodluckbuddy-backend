package com.goodluck_buddy.domain.auth.exception.code;

import com.goodluck_buddy.global.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements BaseSuccessCode {
    REISSUE_OK(HttpStatus.OK,
            "AUTH200_1",
            "토큰이 재발급되었습니다."),
    LOGOUT_OK(HttpStatus.OK,
            "AUTH200_2",
            "로그아웃 되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
