package com.goodluck_buddy.domain.auth.handler;

import com.goodluck_buddy.domain.auth.dto.TokenDto;
import com.goodluck_buddy.domain.auth.entity.CustomOAuth2User;
import com.goodluck_buddy.domain.auth.service.TokenService;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

        TokenDto.Tokens tokenDto = tokenService.issueTokens(user);
        CookieUtil.addCookie(response, "refreshToken", tokenDto.getRefreshToken());

        String redirectUri = (String) request.getSession().getAttribute("redirect_uri");

        if (redirectUri != null && !redirectUri.equals("/")) {
            response.sendRedirect("http://localhost:5173" + redirectUri + "?token=" + tokenDto.getAccessToken());
        } else if (principal.isNew()) {
            response.sendRedirect("http://localhost:5173/signup?token=" + tokenDto.getAccessToken());
        } else {
            response.sendRedirect("http://localhost:5173/home?token=" + tokenDto.getAccessToken());
        }
    }
}
