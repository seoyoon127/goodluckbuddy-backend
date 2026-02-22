package com.goodluck_buddy.domain.reply.controller;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.SortType;
import com.goodluck_buddy.domain.reply.dto.ReplyReqDto;
import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.domain.reply.exception.code.ReplySuccessCode;
import com.goodluck_buddy.domain.reply.service.ReplyService;
import com.goodluck_buddy.global.jwt.JwtUtil;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
@Tag(name = "Reply", description = "답글 관련 API")
public class ReplyController implements ReplyControllerDocs {

    private final ReplyService replyService;
    private final JwtUtil jwtUtil;

    @GetMapping("/letters/{id}")
    public ApiResponse<List<ReplyResDto.Reply>> getReplies(@PathVariable Long id) {
        List<ReplyResDto.Reply> respone = replyService.getReplies(id);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_GET_OK, respone);
    }

    @PostMapping("/letters/{id}")
    public ApiResponse<Void> saveReply(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long id,
            @Valid @RequestBody ReplyReqDto.Reply dto) {
        replyService.saveReply(accessToken, id, dto);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_SAVE_OK, null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReply(@RequestHeader("Authorization") String accessToken, @PathVariable Long id) {
        replyService.deleteReply(accessToken, id);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_DELETE_OK, null);
    }

    @GetMapping("/me")
    public ApiResponse<List<ReplyResDto.ReplyPreview>> getMyReplies(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort) {
        Long userId = parseLong(jwtUtil.getId(accessToken.split(" ")[1]));
        List<ReplyResDto.ReplyPreview> response = replyService.getReplies(category, userId, sort);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_GET_OK, response);
    }

    @GetMapping("/writer/{userId}")
    public ApiResponse<List<ReplyResDto.ReplyPreview>> getUserReplies(
            @RequestParam(required = false) Category category,
            @RequestParam SortType sort,
            @PathVariable Long userId) {
        List<ReplyResDto.ReplyPreview> response = replyService.getReplies(category, userId, sort);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_GET_OK, response);
    }
}
