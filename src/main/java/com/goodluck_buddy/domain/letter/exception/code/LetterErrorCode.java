package com.goodluck_buddy.domain.letter.exception.code;

import com.goodluck_buddy.global.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LetterErrorCode implements BaseErrorCode {
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "LETTER404",
            "해당 편지를 찾지 못했습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,
            "CATEGORY404",
            "해당 카테고리를 찾지 못했습니다."),
    INFO_NOT_FOUND(HttpStatus.NOT_FOUND,
            "INFO404",
            "해당 정보 키워드가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
