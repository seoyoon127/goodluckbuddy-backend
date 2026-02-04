package com.goodluck_buddy.global.exception.handler;

import com.goodluck_buddy.global.code.BaseErrorCode;
import com.goodluck_buddy.global.code.GeneralErrorCode;
import com.goodluck_buddy.global.exception.GeneralException;
import com.goodluck_buddy.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 애플리케이션에서 발생하는 커스텀 예외 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(GeneralException ex) {
        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(ex.getCode(), null));
    }

    // enum이 잘못되었을 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleEnumParseException(
            HttpMessageNotReadableException ex) {

        BaseErrorCode code = GeneralErrorCode.INVALID_ENUM_VALUE;

        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }


    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }
}
