package com.goodluck_buddy.domain.letter.service;

import com.goodluck_buddy.domain.letter.converter.LetterConverter;
import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.entity.Categories;
import com.goodluck_buddy.domain.letter.entity.Info;
import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.CategoriesRepository;
import com.goodluck_buddy.domain.letter.repository.InfoRepository;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.letter.repository.mapping.LetterInfoRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final InfoRepository infoRepository;
    private final LetterInfoRepository letterInfoRepository;
    private final CategoriesRepository categoriesRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void saveLetter(String accessToken, LetterReqDto.Letter dto) {
        Long userId = findUserIdByAccessToken(accessToken);

        Categories category = categoriesRepository
                .findByNameAndParentCategory(dto.getCategory(), dto.getParentCategory())
                .orElseThrow(() -> new LetterException(LetterErrorCode.CATEGORY_NOT_FOUND));

        Letter letter = LetterConverter.toLetter(userId, dto, category);
        letterRepository.save(letter);

        for (String i : dto.getInfoNames()) {
            Info info = infoRepository.findByName(i)
                    .orElseThrow(() -> new LetterException(LetterErrorCode.INFO_NOT_FOUND));
            LetterInfo letterInfo = LetterConverter.toLetterInfo(letter, info);
            letterInfoRepository.save(letterInfo);
        }
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
