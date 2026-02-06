package com.goodluck_buddy.domain.like.service;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.like.converter.LikeConverter;
import com.goodluck_buddy.domain.like.entity.Like;
import com.goodluck_buddy.domain.like.repository.LikeRepository;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final LetterRepository letterRepository;
    private final LikeRepository likeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void saveLike(String accessToken, Long letterId) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        Like like = LikeConverter.toLike(letter, user);
        likeRepository.save(like);
        letter.addLike();
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
