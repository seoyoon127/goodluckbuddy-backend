package com.goodluck_buddy.domain.letter.exception.code;

import com.goodluck_buddy.global.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LetterSuccessCode implements BaseSuccessCode {
    LETTER_SAVE_OK(HttpStatus.OK,
            "LETTER200_1",
            "편지가 저장되었습니다."),
    LETTERS_GET_OK(HttpStatus.OK,
            "LETTER200_2",
            "편지 목록이 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
