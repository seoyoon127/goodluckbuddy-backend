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
import com.goodluck_buddy.domain.like.repository.LikeRepository;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.domain.user.exception.UserException;
import com.goodluck_buddy.domain.user.exception.code.UserErrorCode;
import com.goodluck_buddy.domain.user.repository.UserRepository;
import com.goodluck_buddy.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final InfoRepository infoRepository;
    private final LetterInfoRepository letterInfoRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void saveLetter(String accessToken, LetterReqDto.Letter dto) {
        Long userId = findUserIdByAccessToken(accessToken);

        Categories category = categoriesRepository
                .findByCategory(dto.getCategory())
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

    public List<LetterResDto.Letter> getLetters(Category category, Long id, SortType sortType) {
        if (sortType == null) {
            throw new LetterException(LetterErrorCode.NO_SORT);
        }
        Sort sort = switch (sortType) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case LIKE -> Sort.by(Sort.Direction.DESC, "likeCount");
        };

        Category filterCategory =
                (category == Category.ALL) ? null : category;
        List<Letter> letters = letterRepository.findAllByFilters(filterCategory, id, sort);
        return letters.stream()
                .map(letter -> {
                    User writer = userRepository.findById(letter.getWriterId())
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
                    return LetterConverter.toLetterRes(letter, writer.getNickname(), writer.getId());
                })
                .toList();
    }

    public List<LetterResDto.Letter> getLikeLetters(Category category, Long id, SortType sortType) {
        if (sortType == null) {
            throw new LetterException(LetterErrorCode.NO_SORT);
        }
        Sort sort = switch (sortType) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case LIKE -> Sort.by(Sort.Direction.DESC, "likeCount");
        };

        Category filterCategory =
                (category == Category.ALL) ? null : category;
        List<Letter> letters = letterRepository.findAllByFiltersWithLike(filterCategory, id, sort);
        return letters.stream()
                .map(letter -> {
                    User writer = userRepository.findById(letter.getWriterId())
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
                    return LetterConverter.toLetterRes(letter, writer.getNickname(), writer.getId());
                })
                .toList();
    }

    public LetterResDto.RecommendLetter getRecommendLetters(String accessToken) {
        Long userId = findUserIdByAccessToken(accessToken);
        List<Letter> letters = letterRepository.findRecommendLetters(userId);
        List<LetterResDto.Letter> letterRes = letters.stream()
                .map(letter -> {
                    User writer = userRepository.findById(letter.getWriterId())
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
                    return LetterConverter.toLetterRes(letter, writer.getNickname(), writer.getId());
                })
                .toList();

        return LetterConverter.toRecommendLetterRes(letterRes, getPhrase(userId));
    }

    @Transactional(readOnly = true)
    public LetterResDto.LetterDetail getLetter(Long letterId, Long userId) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        User writer = userRepository.findById(letter.getWriterId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        boolean like = false;
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
            like = likeRepository.existsByUserAndLetter(user, letter);
        }
        final boolean isLiked = like;
        boolean isMine = userId != null && userId.equals(writer.getId());
        List<Info> infos = letter.getInfos();
        return LetterConverter.toLetterDetailRes(letter, writer.getNickname(), infos, isMine, isLiked, writer.getId());
    }

    @Transactional
    public void updateLetter(String accessToken, LetterReqDto.LetterUpdate dto, Long letterId) {
        Long userId = findUserIdByAccessToken(accessToken);
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        User writer = userRepository.findById(letter.getWriterId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

        if (!userId.equals(writer.getId())) {
            throw new LetterException(LetterErrorCode.INVALID_WRITER);
        }

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            letter.updateTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            letter.updateContent(dto.getContent());
        }
        if (dto.getLetterDesign() != null) {
            letter.updateLetterDesign(dto.getLetterDesign());
        }
        if (dto.getCategory() != null) {
            Categories category = categoriesRepository
                    .findByCategory(dto.getCategory())
                    .orElseThrow(() -> new LetterException(LetterErrorCode.CATEGORY_NOT_FOUND));
            letter.updateCategories(category);
        }
        if (dto.getInfoNames() != null) {
            letter.updateInfos(dto.getInfoNames().stream()
                    .map(i -> infoRepository.findByName(i)
                            .orElseThrow(() -> new LetterException(LetterErrorCode.INFO_NOT_FOUND))).toList());
        }
    }

    @Transactional
    public void deleteLetter(String accessToken, Long letterId) {
        Long userId = findUserIdByAccessToken(accessToken);
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.LETTER_NOT_FOUND));
        if (!userId.equals(letter.getWriterId())) {
            throw new LetterException(LetterErrorCode.INVALID_WRITER);
        }
        letterRepository.delete(letter);
    }

    private Long findUserIdByAccessToken(String accessToken) {
        String token = accessToken.split(" ")[1];
        return Long.parseLong(jwtUtil.getId(token));
    }

    private String getPhrase(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        int ageGroup =
                (LocalDate.now().getYear() - user.getBirth().getYear()) / 10 * 10;
        String gender = switch (user.getGender()) {
            case MALE -> "남성";
            case FEMALE -> "여성";
            case NONE -> null;
        };
        if (gender != null) {
            return ageGroup + "대 " + gender + "이 좋아요를 많이한 편지예요.";
        }
        return ageGroup + "대가 좋아요를 많이한 편지예요";
    }
}
