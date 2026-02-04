package com.goodluck_buddy.domain.user.service;

import com.goodluck_buddy.domain.user.dto.UserReqDto;
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
        checkNickname(dto.getNickname());
        userRepository.updateProfile(userId, dto.getNickname(), dto.getGender(), dto.getBirth(), dto.getCategory());
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
