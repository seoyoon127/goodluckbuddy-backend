package com.goodluck_buddy.domain.auth.service;

import com.goodluck_buddy.domain.auth.dto.TokenDto;
import com.goodluck_buddy.domain.auth.entity.RefreshToken;
import com.goodluck_buddy.domain.auth.exception.AuthException;
import com.goodluck_buddy.domain.auth.exception.code.AuthErrorCode;
import com.goodluck_buddy.domain.auth.repository.RefreshTokenRepository;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // 토큰 발급
    @Transactional
    public TokenDto.Tokens issueTokens(User user) {
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshTokenValue = jwtUtil.createRefreshToken(user);
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId())
                .map(rt -> {
                    rt.update(refreshTokenValue, LocalDateTime.now().plusDays(1));
                    return rt;
                })
                .orElse(
                        new RefreshToken(refreshTokenValue, user, LocalDateTime.now().plusDays(1))
                );


        refreshTokenRepository.save(refreshToken);

        return new TokenDto.Tokens(accessToken, refreshTokenValue);
    }

    // 토큰 재발급
    @Transactional
    public TokenDto.Tokens reissueTokens(TokenDto.RefreshToken dto) {
        String refreshTokenValue = dto.getRefreshToken();
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new AuthException(AuthErrorCode.TOKEN_NOT_FOUND));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }

        User user = refreshToken.getUser();

        String newRefreshTokenValue = jwtUtil.createRefreshToken(user);
        String accessToken = jwtUtil.createAccessToken(user);

        refreshToken.update(newRefreshTokenValue, LocalDateTime.now().plusDays(1));

        return new TokenDto.Tokens(accessToken, newRefreshTokenValue);
    }

    // 로그아웃
    public void logout(TokenDto.RefreshToken dto) {
        String refreshTokenValue = dto.getRefreshToken();
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new AuthException(AuthErrorCode.TOKEN_NOT_FOUND));
        refreshTokenRepository.delete(refreshToken);
    }
}
