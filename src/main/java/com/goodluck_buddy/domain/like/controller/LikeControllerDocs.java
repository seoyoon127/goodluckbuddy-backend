package com.goodluck_buddy.domain.like.controller;

import com.goodluck_buddy.domain.like.dto.LikeResDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface LikeControllerDocs {

    @Operation(
            summary = "편지 좋아요 선택",
            description = "해당 편지의 좋아요를 1 증가시킨다."
    )
    ApiResponse<LikeResDto.Likes> saveLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId);

    @Operation(
            summary = "편지 좋아요 취소",
            description = "해당 편지의 좋아요를 삭제한다."
    )
    ApiResponse<LikeResDto.Likes> deleteLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId);

    @Operation(
            summary = "답글 좋아요 선택",
            description = "해당 답글의 좋아요를 1 증가시킨다."
    )
    ApiResponse<LikeResDto.Likes> saveReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId);

    @Operation(
            summary = "답글 좋아요 취소",
            description = "해당 답글의 좋아요를 삭제한다."
    )
    ApiResponse<LikeResDto.Likes> deleteReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId);
}
