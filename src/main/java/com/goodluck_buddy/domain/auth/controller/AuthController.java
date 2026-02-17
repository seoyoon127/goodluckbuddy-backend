package com.goodluck_buddy.domain.auth.controller;

import com.goodluck_buddy.domain.auth.dto.TokenDto;
import com.goodluck_buddy.domain.auth.exception.code.AuthSuccessCode;
import com.goodluck_buddy.domain.auth.service.TokenService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 api")
public class AuthController implements AuthControllerDocs {

    private final TokenService tokenService;

    @PostMapping("/token/reissue")
    public ApiResponse<TokenDto.AccessToken> tokenReissue(
            @CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        TokenDto.AccessToken accessToken = tokenService.reissueTokens(refreshToken, response);
        return ApiResponse.onSuccess(AuthSuccessCode.REISSUE_OK, accessToken);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @CookieValue("refreshToken") String refreshToken) {
        tokenService.logout(refreshToken);
        return ApiResponse.onSuccess(AuthSuccessCode.LOGOUT_OK, null);
    }
}
