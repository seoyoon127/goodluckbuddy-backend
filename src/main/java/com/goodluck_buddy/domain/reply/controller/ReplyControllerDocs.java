package com.goodluck_buddy.domain.reply.controller;

import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReplyControllerDocs {

    @Operation(
            summary = "답글 조회",
            description = "해당 편지의 답글을 조회합니다."
    )
    ApiResponse<List<ReplyResDto.Reply>> getReplies(@PathVariable Long id);
}
