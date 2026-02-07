package com.goodluck_buddy.domain.reply.service;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.reply.converter.ReplyConverter;
import com.goodluck_buddy.domain.reply.dto.ReplyReqDto;
import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;

    public List<ReplyResDto.Reply> getReplies(Long id) {
        Letter letter = letterRepository.findById(id)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        return letter.getReplies().stream()
                .map(r -> {
                    User writer = userRepository.findById(letter.getWriterId())
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
                    return ReplyConverter.toReplyRes(r, writer.getNickname());
                }).toList();
    }

    @Transactional
    public void saveReply(String accessToken, Long id, ReplyReqDto.Reply dto) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        Letter letter = letterRepository.findById(id)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        Reply reply = ReplyConverter.toReply(dto, user, letter);
        replyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(String accessToken, Long replyId) {
        Long userId = findUserIdByAccessToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(ReplyErrorCode.REPLY_NOT_FOUND));

        if (!user.equals(reply.getUser())) {
            throw new ReplyException(ReplyErrorCode.INVALID_WRITER);
        }
        replyRepository.delete(reply);
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
