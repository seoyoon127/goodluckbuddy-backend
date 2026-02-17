package com.goodluck_buddy.domain.auth.exception;

import com.goodluck_buddy.global.code.BaseErrorCode;
import com.goodluck_buddy.global.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode code) {
        super(code);
    }
}
