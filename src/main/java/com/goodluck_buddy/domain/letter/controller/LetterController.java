package com.goodluck_buddy.domain.letter.controller;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.dto.LetterResDto;
import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.SortType;
import com.goodluck_buddy.domain.letter.exception.code.LetterSuccessCode;
import com.goodluck_buddy.domain.letter.service.LetterService;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.global.jwt.JwtUtil;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "Letter", description = "편지 관련 API")
public class LetterController implements LetterControllerDocs {

    private final LetterService letterService;
    private final JwtUtil jwtUtil;

    @PostMapping("")
    public ApiResponse<Void> saveLetter(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody LetterReqDto.Letter dto) {
        letterService.saveLetter(accessToken, dto);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTER_SAVE_OK, null);
    }

    @GetMapping("")
    public ApiResponse<List<LetterResDto.Letter>> getLetters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Category parentCategory,
            @RequestParam SortType sort
    ) {
        List<LetterResDto.Letter> reponse = letterService.getLetters(category, parentCategory, null, sort);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTERS_GET_OK, reponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<LetterResDto.LetterDetail> getLetter(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Long userId = (user != null) ? user.getId() : null;
        LetterResDto.LetterDetail response = letterService.getLetter(id, userId);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTER_GET_OK, response);
    }

    @GetMapping("/me")
    public ApiResponse<List<LetterResDto.Letter>> getMyLetters(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Category parentCategory,
            @RequestParam SortType sort) {
        Long userId = parseLong(jwtUtil.getId(accessToken.split(" ")[1]));
        List<LetterResDto.Letter> reponse = letterService.getLetters(category, parentCategory, userId, sort);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTERS_GET_OK, reponse);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> updateMyLetter(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody LetterReqDto.LetterUpdate dto,
            @PathVariable Long id) {
        letterService.updateLetter(accessToken, dto, id);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTER_PATCH_OK, null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMyLetter(@RequestHeader("Authorization") String accessToken, @PathVariable Long id) {
        letterService.deleteLetter(accessToken, id);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTER_DELETE_OK, null);
    }

    @GetMapping("/writer/{userId}")
    public ApiResponse<List<LetterResDto.Letter>> getUserLetters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Category parentCategory,
            @RequestParam SortType sort,
            @PathVariable Long userId) {
        List<LetterResDto.Letter> reponse = letterService.getLetters(category, parentCategory, userId, sort);
        return ApiResponse.onSuccess(LetterSuccessCode.LETTERS_GET_OK, reponse);
    }
}
