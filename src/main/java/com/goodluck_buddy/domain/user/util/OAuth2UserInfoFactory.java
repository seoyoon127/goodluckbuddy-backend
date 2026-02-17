package com.goodluck_buddy.domain.user.util;

import com.goodluck_buddy.domain.auth.entity.GoogleUserInfo;
import com.goodluck_buddy.domain.auth.entity.KakaoUserInfo;
import com.goodluck_buddy.domain.auth.entity.OAuth2UserInfo;
import com.goodluck_buddy.domain.auth.exception.AuthException;
import com.goodluck_buddy.domain.auth.exception.code.AuthErrorCode;
import com.goodluck_buddy.domain.user.enums.SocialType;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        return switch (toSocialType(registrationId)) {
            case GOOGLE -> new GoogleUserInfo(attributes);
            case KAKAO -> new KakaoUserInfo(attributes);
            default -> throw new AuthException(AuthErrorCode.INVALID_SOCIAL_TYPE);
        };
    }

    private static SocialType toSocialType(String registrationId) {
        try {
            return SocialType.valueOf(registrationId.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AuthException(AuthErrorCode.INVALID_SOCIAL_TYPE);
        }
    }
}
