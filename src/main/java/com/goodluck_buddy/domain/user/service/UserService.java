package com.goodluck_buddy.domain.user.service;

import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void checkNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new UserException(UserErrorCode.DUPLICATE_USER);
        }
    }
}
