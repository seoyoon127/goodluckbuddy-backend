package com.goodluck_buddy.domain.user.exception.code;

import com.goodluck_buddy.global.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {
    VALID_NICKNAME(HttpStatus.OK,
            "USER200_1",
            "사용 가능한 닉네임입니다."),
    PROFILE_SAVE_OK(HttpStatus.OK,
            "USER200_2",
            "프로필이 저장되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
