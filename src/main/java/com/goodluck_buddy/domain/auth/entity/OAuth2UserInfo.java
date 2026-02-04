package com.goodluck_buddy.domain.auth.entity;

import com.goodluck_buddy.domain.user.enums.SocialType;

import java.util.Map;

public interface OAuth2UserInfo {
    String getProviderId();

    SocialType getProvider();

    Map<String, Object> getAttributes();
}
