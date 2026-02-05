package com.goodluck_buddy.domain.letter.controller;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.exception.code.LetterSuccessCode;
import com.goodluck_buddy.domain.letter.service.LetterService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "Letter", description = "편지 관련 API")
public class LetterController implements LetterControllerDocs {

    private final LetterService letterService;

    @PostMapping("")
    public ApiResponse<Void> saveLetter(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody LetterReqDto.Letter dto) {
        letterService.saveLetter(accessToken, dto);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTER_SAVE_OK, null);
    }
}
