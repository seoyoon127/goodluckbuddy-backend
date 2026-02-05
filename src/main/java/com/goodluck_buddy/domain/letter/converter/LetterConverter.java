package com.goodluck_buddy.domain.letter.converter;

import com.goodluck_buddy.domain.letter.dto.LetterReqDto;
import com.goodluck_buddy.domain.letter.entity.Categories;
import com.goodluck_buddy.domain.letter.entity.Info;
import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;

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
}
