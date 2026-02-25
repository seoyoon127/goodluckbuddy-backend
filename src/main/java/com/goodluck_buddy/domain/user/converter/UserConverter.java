package com.goodluck_buddy.domain.user.converter;

import com.goodluck_buddy.domain.auth.entity.OAuth2UserInfo;
import com.goodluck_buddy.domain.user.dto.UserResDto;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.enums.Status;

public class UserConverter {

    public static User toOAuthUser(
            OAuth2UserInfo userInfo
    ) {
        return User.builder()
                .providerId(userInfo.getProviderId())
                .socialType(userInfo.getProvider())
                .status(Status.ACTIVATE)
                .build();
    }

    public static UserResDto.Profile toMyProfile(
            User user
    ) {
        return UserResDto.Profile.builder()
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birth(user.getBirth())
                .category(user.getInterestCategory())
                .id(user.getId())
                .build();
    }
}
