package com.goodluck_buddy.domain.auth.entity;

import com.goodluck_buddy.domain.user.enums.SocialType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Long userId;
    private final String providerId;
    private final SocialType socialType;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Long userId, String providerId,
                            SocialType socialType,
                            Map<String, Object> attributes) {
        this.userId = userId;
        this.providerId = providerId;
        this.socialType = socialType;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return providerId;
    }
}

