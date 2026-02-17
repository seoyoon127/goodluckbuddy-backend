package com.goodluck_buddy.domain.user.exception;

import com.goodluck_buddy.global.code.BaseErrorCode;
import com.goodluck_buddy.global.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode code) {
        super(code);
    }
}
