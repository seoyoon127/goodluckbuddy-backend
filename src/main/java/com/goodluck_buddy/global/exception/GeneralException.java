package com.goodluck_buddy.global.exception;

import com.goodluck_buddy.global.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private final BaseErrorCode code;
}
