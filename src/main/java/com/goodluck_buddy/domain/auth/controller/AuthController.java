package com.goodluck_buddy.domain.auth.controller;

import com.goodluck_buddy.domain.auth.dto.TokenDto;
import com.goodluck_buddy.domain.auth.exception.code.AuthSuccessCode;
import com.goodluck_buddy.domain.auth.service.TokenService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 api")
public class AuthController implements AuthControllerDocs {

    private final TokenService tokenService;

    @PostMapping("/token/reissue")
    public ApiResponse<TokenDto.Tokens> tokenReissue(@Valid @RequestBody TokenDto.RefreshToken dto) {
        TokenDto.Tokens tokens = tokenService.reissueTokens(dto);
        return ApiResponse.onSuccess(AuthSuccessCode.REISSUE_OK, tokens);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody TokenDto.RefreshToken dto) {
        tokenService.logout(dto);
        return ApiResponse.onSuccess(AuthSuccessCode.LOGOUT_OK, null);
    }
}
