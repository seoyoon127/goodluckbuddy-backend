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
            "해당 정보 키워드가 존재하지 않습니다."),
    NO_SORT(HttpStatus.BAD_REQUEST,
            "SORT400",
            "정렬 방식을 선택해야합니다."),
    INVALID_WRITER(HttpStatus.BAD_REQUEST,
            "WRITER400",
            "작성자만 접근할 수 있는 기능입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
