package com.goodluck_buddy.domain.like.controller;

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
    public ApiResponse<Void> saveLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId) {
        likeService.saveLike(accessToken, letterId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_SAVE_OK, null);
    }

    @DeleteMapping("/letters/{letterId}/like")
    public ApiResponse<Void> deleteLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long letterId) {
        likeService.deleteLike(accessToken, letterId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_DELETE_OK, null);
    }

    @PostMapping("/replies/{replyId}/like")
    public ApiResponse<Void> saveReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId) {
        likeService.saveReplyLike(accessToken, replyId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_SAVE_OK, null);
    }

    @DeleteMapping("/replies/{replyId}/like")
    public ApiResponse<Void> deleteReplyLike(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long replyId) {
        likeService.deleteReplyLike(accessToken, replyId);
        return ApiResponse.onSuccess(LikeSuccessCode.LIKE_DELETE_OK, null);
    }
}
