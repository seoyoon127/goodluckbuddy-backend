package com.goodluck_buddy.domain.auth.entity;

import com.goodluck_buddy.domain.user.enums.SocialType;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public SocialType getProvider() {
        return SocialType.KAKAO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
}
