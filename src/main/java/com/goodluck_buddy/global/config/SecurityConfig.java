package com.goodluck_buddy.global.config;

import com.goodluck_buddy.domain.auth.handler.OAuth2SuccessHandler;
import com.goodluck_buddy.domain.auth.service.OAuth2UserCustomService;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtAuthFilter;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final String[] allowUris = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/auth/**",
            "/api/letters/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OAuth2UserCustomService oAuth2UserCustomService, OAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .requestMatchers(POST, "/api/letters/**").authenticated()
                        .requestMatchers(PATCH, "/api/letters/**").authenticated()
                        .requestMatchers(DELETE, "/api/letters/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("""
                                    {
                                      "code": "LOGIN_REQUIRED",
                                      "message": "로그인이 필요한 서비스입니다."
                                    }
                                    """);
                        }))
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserCustomService))
                        .successHandler(oAuth2SuccessHandler));

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userRepository);
    }

}
