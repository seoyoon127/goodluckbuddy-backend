package com.goodluck_buddy.domain.letter.controller;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface LetterControllerDocs {

    @Operation(
            summary = "편지 쓰기",
            description = "편지를 저장합니다."
    )
    ApiResponse<Void> saveLetter(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody LetterReqDto.Letter dto);
}
