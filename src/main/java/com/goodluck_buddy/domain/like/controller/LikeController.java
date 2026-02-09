package com.goodluck_buddy.domain.like.controller;

import com.goodluck_buddy.domain.like.dto.LikeResDto;
import com.goodluck_buddy.domain.like.exception.code.LikeSuccessCode;
import com.goodluck_buddy.domain.like.service.LikeService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Like", description = "좋아요 관련 API")
public class LikeController implements LikeControllerDocs {

    private final LikeService likeService;

    @PostMapping("/letters/{letterId}/like")
    public ApiResponse<LikeResDto.Likes> saveLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId) {
        LikeResDto.Likes response = likeService.saveLike(accessToken, letterId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_SAVE_OK, response);
    }

    @DeleteMapping("/letters/{letterId}/like")
    public ApiResponse<LikeResDto.Likes> deleteLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId) {
        LikeResDto.Likes response = likeService.deleteLike(accessToken, letterId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_DELETE_OK, response);
    }

    @PostMapping("/replies/{replyId}/like")
    public ApiResponse<LikeResDto.Likes> saveReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId) {
        LikeResDto.Likes response = likeService.saveReplyLike(accessToken, replyId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_SAVE_OK, response);
    }

    @DeleteMapping("/replies/{replyId}/like")
    public ApiResponse<LikeResDto.Likes> deleteReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId) {
        LikeResDto.Likes response = likeService.deleteReplyLike(accessToken, replyId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_DELETE_OK, response);
    }
}
