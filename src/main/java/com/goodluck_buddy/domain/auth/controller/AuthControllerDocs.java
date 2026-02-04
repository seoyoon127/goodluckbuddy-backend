package com.goodluck_buddy.domain.auth.controller;

import com.goodluck_buddy.domain.auth.dto.TokenDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(
            summary = "로그인 유지",
            description = "새로운 토큰을 발급합니다. (accessToken 30분, refreshToken 1일)"
    )
    ApiResponse<TokenDto.Tokens> tokenReissue(@Valid @RequestBody TokenDto.RefreshToken dto);

    @Operation(
            summary = "로그아웃",
            description = "저장된 토큰을 삭제합니다."
    )
    ApiResponse<Void> logout(@Valid @RequestBody TokenDto.RefreshToken dto);
}
