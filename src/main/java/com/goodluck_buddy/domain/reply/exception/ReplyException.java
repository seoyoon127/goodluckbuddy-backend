package com.goodluck_buddy.domain.reply.exception;

import com.goodluck_buddy.global.code.BaseErrorCode;
import com.goodluck_buddy.global.exception.GeneralException;

public class ReplyException extends GeneralException {
    public ReplyException(BaseErrorCode code) {
        super(code);
    }
}
