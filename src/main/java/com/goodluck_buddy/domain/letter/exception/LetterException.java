package com.goodluck_buddy.domain.letter.exception;

import com.goodluck_buddy.global.code.BaseErrorCode;
import com.goodluck_buddy.global.exception.GeneralException;

public class LetterException extends GeneralException {
    public LetterException(BaseErrorCode code) {
        super(code);
    }
}
