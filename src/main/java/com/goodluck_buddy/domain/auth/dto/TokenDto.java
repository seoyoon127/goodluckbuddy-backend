package com.goodluck_buddy.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class TokenDto {

    @Getter
    @AllArgsConstructor
    public static class Tokens {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    public static class RefreshToken {
        @NotBlank
        private String refreshToken;
    }
}