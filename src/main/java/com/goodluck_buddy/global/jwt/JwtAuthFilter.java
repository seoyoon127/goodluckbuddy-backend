package com.goodluck_buddy.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.code.GeneralErrorCode;
import com.goodluck_buddy.global.response.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 토큰 가져오기
        String token = request.getHeader("Authorization");
        // token이 없거나 Bearer가 아니면 넘기기
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Bearer이면 추출
        token = token.replace("Bearer ", "");
        // AccessToken 검증하기: 올바른 토큰이면
        try {
            Claims claims = jwtUtil.getClaims(token);
            Long userId = Long.parseLong(jwtUtil.getId(token));
            System.out.println("filter- userId:" + userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    null
            );
            // 인증 완료 후 SecurityContextHolder에 넣기
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            ApiResponse<Void> body =
                    ApiResponse.onFailure(GeneralErrorCode.UNAUTHORIZED, null);

            response.getWriter().write(
                    objectMapper.writeValueAsString(body)
            );
            return;
        }
        filterChain.doFilter(request, response);
    }
}