package com.goodluck_buddy.domain.reply.controller;

import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.domain.reply.exception.code.ReplySuccessCode;
import com.goodluck_buddy.domain.reply.service.ReplyService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
@Tag(name = "Reply", description = "답글 관련 API")
public class ReplyController implements ReplyControllerDocs {

    private final ReplyService replyService;

    @GetMapping("/letters/{id}")
    public ApiResponse<List<ReplyResDto.Reply>> getReplies(@PathVariable Long id) {
        List<ReplyResDto.Reply> respone = replyService.getReplies(id);
        return ApiResponse.onSuccess(ReplySuccessCode.REPLY_GET_OK, respone);
    }
}
