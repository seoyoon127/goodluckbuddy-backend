package com.goodluck_buddy.domain.auth.service;

import com.goodluck_buddy.domain.auth.entity.CustomOAuth2User;
import com.goodluck_buddy.domain.auth.entity.OAuth2UserInfo;
import com.goodluck_buddy.domain.auth.exception.AuthException;
import com.goodluck_buddy.domain.auth.exception.code.AuthErrorCode;
import com.goodluck_buddy.domain.user.converter.UserConverter;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.domain.user.util.OAuth2UserInfoFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        User user = saveOrUpdate(userInfo);
        return new CustomOAuth2User(
                user.getId(),
                user.getProviderId(),
                user.getSocialType(),
                oAuth2User.getAttributes()
        );
    }

    private User saveOrUpdate(OAuth2UserInfo userInfo) {
        User user = userRepository.findBySocialTypeAndProviderId(
                        userInfo.getProvider(),
                        userInfo.getProviderId()
                )
                .map(u -> {
                    if (!u.getSocialType().equals(userInfo.getProvider())) {
                        throw new AuthException(AuthErrorCode.DUPLICATE_SOCIAL);
                    }
                    return u;
                })
                .orElse(
                        UserConverter.toOAuthUser(userInfo)
                );
        return userRepository.save(user);
    }
}
