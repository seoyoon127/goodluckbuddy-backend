package com.goodluck_buddy.domain.auth.entity;

import com.goodluck_buddy.domain.user.enums.SocialType;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public SocialType getProvider() {
        return SocialType.GOOGLE;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
}
