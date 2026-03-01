package com.goodluck_buddy.domain.user.service;

import com.goodluck_buddy.domain.user.converter.UserConverter;
import com.goodluck_buddy.domain.user.dto.UserReqDto;
import com.goodluck_buddy.domain.user.dto.UserResDto;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.enums.Status;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void checkNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new UserException(UserErrorCode.DUPLICATE_USER);
        }
    }

    @Transactional
    public void updateProfile(String accessToken, UserReqDto.Profile dto) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

        if (dto.getNickname() != null) {
            checkNickname(dto.getNickname());
            user.updateNickname(dto.getNickname());
        }

        if (dto.getGender() != null) {
            user.updateGender(dto.getGender());
        }

        if (dto.getBirth() != null) {
            user.updateBirth(dto.getBirth());
        }

        if (dto.getCategory() != null) {
            user.updateCategory(dto.getCategory());
        }

    }

    public UserResDto.Profile getMyProfile(String accessToken) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        return UserConverter.toMyProfile(user);
    }

    @Transactional
    public void withdraw(String accessToken) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        user.updateNickname("탈퇴한 사용자");
        String deletedProviderId = "deleted-" + userId;
        userRepository.withdraw(userId, Status.INACTIVATE, deletedProviderId);
    }

    public UserResDto.Profile getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        return UserConverter.toMyProfile(user);
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
