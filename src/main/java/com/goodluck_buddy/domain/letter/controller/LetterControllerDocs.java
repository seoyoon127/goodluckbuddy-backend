package com.goodluck_buddy.domain.letter.controller;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.dto.LetterResDto;
import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.SortType;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LetterControllerDocs {

    @Operation(
            summary = "편지 쓰기",
            description = "편지를 저장합니다."
    )
    ApiResponse<Void> saveLetter(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody LetterReqDto.Letter dto);

    @Operation(
            summary = "편지 목록 조회",
            description = "편지 목록을 조회합니다."
    )
    ApiResponse<List<LetterResDto.Letter>> getLetters(
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort
    );

    @Operation(
            summary = "편지 상세 조회",
            description = "편지 세부 내용을 조회합니다."
    )
    ApiResponse<LetterResDto.LetterDetail> getLetter(@PathVariable Long id, @AuthenticationPrincipal User user);

    @Operation(
            summary = "내 편지 목록 조회",
            description = "내가 작성한 편지 목록을 조회합니다."
    )
    ApiResponse<List<LetterResDto.Letter>> getMyLetters(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort);

    @Operation(
            summary = "내 편지 수정",
            description = "해당 편지를 수정합니다."
    )
    ApiResponse<Void> updateMyLetter(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody LetterReqDto.LetterUpdate dto,
            @PathVariable Long id);

    @Operation(
            summary = "내 편지 삭제",
            description = "해당 편지를 삭제합니다."
    )
    ApiResponse<Void> deleteMyLetter(@RequestHeader("Authorization") String accessToken, @PathVariable Long id);

    @Operation(
            summary = "타인 편지 목록 조회",
            description = "특정 유저의 편지 목록을 조회합니다."
    )
    ApiResponse<List<LetterResDto.Letter>> getUserLetters(
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort,
            @PathVariable Long userId);

    @Operation(
            summary = "좋아요한 편지 목록 조회",
            description = "내가 좋아요한 편지 목록을 조회합니다."
    )
    ApiResponse<List<LetterResDto.Letter>> getLikeLetters(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort);

    @Operation(
            summary = "추천 편지 목록 조회",
            description = "추천 편지 목록을 조회합니다."
    )
    ApiResponse<LetterResDto.RecommendLetter> getRecommendLetters(
            @RequestHeader("Authorization") String accessToken);
}
