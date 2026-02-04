package com.goodluck_buddy.domain.user.controller;

import com.goodluck_buddy.domain.user.dto.UserReqDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerDocs {

    @Operation(
            summary = "닉네임 중복확인",
            description = "해당 닉네임이 사용중인지 확인합니다."
    )
    ApiResponse<Void> checkNickname(@Valid @RequestBody UserReqDto.Nickname dto);
}
