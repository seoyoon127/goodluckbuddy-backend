package com.goodluck_buddy.domain.reply.controller;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.SortType;
import com.goodluck_buddy.domain.reply.dto.ReplyReqDto;
import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReplyControllerDocs {

    @Operation(
            summary = "답글 목록 조회",
            description = "해당 편지의 답글 목록을 조회합니다."
    )
    ApiResponse<List<ReplyResDto.Reply>> getReplies(@PathVariable Long id);

    @Operation(
            summary = "답글 작성",
            description = "해당 편지에 답글을 저장합니다."
    )
    ApiResponse<Void> saveReply(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long id,
            @Valid @RequestBody ReplyReqDto.Reply dto);

    @Operation(
            summary = "답글 삭제",
            description = "해당 편지에 답글을 삭제합니다."
    )
    ApiResponse<Void> deleteReply(@RequestHeader("Authorization") String accessToken, @PathVariable Long id);

    @Operation(
            summary = "내 답글 목록 조회",
            description = "내가 작성한 답글 목록을 조회합니다."
    )
    ApiResponse<List<ReplyResDto.ReplyPreview>> getMyReplies(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Category parentCategory,
            @RequestParam SortType sort);

    @Operation(
            summary = "타인 답글 목록 조회",
            description = "해당 유저가 작성한 답글 목록을 조회합니다."
    )
    ApiResponse<List<ReplyResDto.ReplyPreview>> getUserReplies(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Category parentCategory,
            @RequestParam SortType sort,
            @PathVariable Long userId);
}
