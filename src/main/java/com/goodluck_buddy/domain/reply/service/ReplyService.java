package com.goodluck_buddy.domain.reply.service;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.reply.converter.ReplyConverter;
import com.goodluck_buddy.domain.reply.dto.ReplyResDto;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

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
}
