package com.goodluck_buddy.domain.letter.converter;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.dto.LetterResDto;
import com.goodluck_buddy.domain.letter.entity.Categories;
import com.goodluck_buddy.domain.letter.entity.Info;
import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;

import java.util.List;

public class LetterConverter {
    public static Letter toLetter(
            Long memberId,
            LetterReqDto.Letter dto,
            Categories categories
    ) {
        return Letter.builder()
                .writerId(memberId)
                .title(dto.getTitle())
                .content(dto.getContent())
                .likeCount(0L)
                .letterDesign(dto.getLetterDesign())
                .categories(categories)
                .build();
    }

    public static LetterInfo toLetterInfo(
            Letter letter,
            Info info
    ) {
        return LetterInfo.builder()
                .letter(letter)
                .info(info)
                .build();
    }

    public static LetterResDto.Letter toLetterRes(
            Letter letter,
            String writerName
    ) {
        return LetterResDto.Letter.builder()
                .letterId(letter.getId())
                .writerName(writerName)
                .title(letter.getTitle())
                .content(letter.getContent())
                .likeCount(letter.getLikeCount())
                .category(letter.getCategories().getCategory())
                .build();
    }

    public static LetterResDto.LetterDetail toLetterDetailRes(
            Letter letter,
            String writerName,
            List<Info> infos,
            boolean isMine
    ) {
        return LetterResDto.LetterDetail.builder()
                .letterId(letter.getId())
                .writerName(writerName)
                .title(letter.getTitle())
                .content(letter.getContent())
                .letterDesign(letter.getLetterDesign())
                .createdAt(letter.getCreatedAt())
                .likeCount(letter.getLikeCount())
                .category(letter.getCategories().getCategory())
                .mine(isMine)
                .infos(infos.stream().map(i -> i.getName()).toList())
                .build();
    }

    public static LetterResDto.RecommendLetter toRecommendLetterRes(
            List<LetterResDto.Letter> letters,
            String phrase
    ) {
        return LetterResDto.RecommendLetter.builder()
                .letters(letters)
                .phrase(phrase)
                .build();
    }
}
