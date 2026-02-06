package com.goodluck_buddy.domain.letter.service;

import com.goodluck_buddy.domain.letter.converter.LetterConverter;
import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.dto.LetterResDto;
import com.goodluck_buddy.domain.letter.entity.Categories;
import com.goodluck_buddy.domain.letter.entity.Info;
import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;
import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.SortType;
import com.goodluck_buddy.domain.letter.exception.LetterException;
import com.goodluck_buddy.domain.letter.exception.code.LetterErrorCode;
import com.goodluck_buddy.domain.letter.repository.CategoriesRepository;
import com.goodluck_buddy.domain.letter.repository.InfoRepository;
import com.goodluck_buddy.domain.letter.repository.LetterRepository;
import com.goodluck_buddy.domain.letter.repository.mapping.LetterInfoRepository;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final InfoRepository infoRepository;
    private final LetterInfoRepository letterInfoRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
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

    public List<LetterResDto.Letter> getLetters(String category, Category parentCategory, SortType sortType) {
        if (sortType == null) {
            throw new LetterException(LetterErrorCode.NO_SORT);
        }
        Sort sort = switch (sortType) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case LIKE -> Sort.by(Sort.Direction.DESC, "likeCount");
        };
        List<Letter> letters = letterRepository.findAllByFilters(category, parentCategory, sort);
        return letters.stream()
                .map(letter -> {
                    User writer = userRepository.findById(letter.getWriterId())
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
                    return LetterConverter.toLetterRes(letter, writer.getNickname());
                })
                .toList();
    }

    public LetterResDto.LetterDetail getLetter(Long letterId, Long userId) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        User writer = userRepository.findById(letter.getWriterId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        boolean isMine = (writer.getId() == userId) ? true : false;
        List<Info> infos = letter.getInfos();
        return LetterConverter.toLetterDetailRes(letter, writer.getNickname(), infos, isMine);
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }
}
