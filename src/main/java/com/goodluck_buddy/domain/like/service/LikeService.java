package com.goodluck_buddy.domain.like.service;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.like.converter.LikeConverter;
import com.goodluck_buddy.domain.like.entity.Like;
import com.goodluck_buddy.domain.like.entity.ReplyLike;
import com.goodluck_buddy.domain.like.repository.LikeRepository;
import com.goodluck_buddy.domain.like.repository.ReplyLikeRepository;
import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.domain.reply.exception.ReplyException;
import com.goodluck_buddy.domain.reply.exception.code.ReplyErrorCode;
import com.goodluck_buddy.domain.reply.repository.ReplyRepository;
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
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void saveLike(String accessToken, Long letterId) {
        User user = getUser(accessToken);
        Letter letter = getLetter(letterId);
        if (!likeRepository.existsByUserAndLetter(user, letter)) {
            Like like = LikeConverter.toLike(letter, user);
            likeRepository.save(like);
            letter.addLike();
        }
    }

    @Transactional
    public void deleteLike(String accessToken, Long letterId) {
        User user = getUser(accessToken);
        Letter letter = getLetter(letterId);
        if (likeRepository.existsByUserAndLetter(user, letter)) {
            likeRepository.deleteByUserAndLetter(user, letter);
            letter.removeLike();
        }
    }

    @Transactional
    public void saveReplyLike(String accessToken, Long replyId) {
        User user = getUser(accessToken);
        Reply reply = getReply(replyId);
        if (!replyLikeRepository.existsByUserAndReply(user, reply)) {
            ReplyLike replyLike = LikeConverter.toReplyLike(reply, user);
            replyLikeRepository.save(replyLike);
            reply.addLike();
        }
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }

    private User getUser(String accessToken) {
        Long userId = findUserIdByAccessToken(accessToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
    }

    private Letter getLetter(Long letterId) {
        return letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
    }

    private Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(ReplyErrorCode.REPLY_NOT_FOUND));
    }
}
