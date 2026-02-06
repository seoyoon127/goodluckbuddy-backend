package com.goodluck_buddy.domain.letter.dto;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.letter.enums.LetterDesign;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

public class LetterReqDto {

    @Getter
    public static class Letter {
        @NotBlank
        @Size(min = 1, max = 25)
        private String title;

        @NotBlank
        @Size(min = 1, max = 200)
        private String content;

        @NotNull
        private LetterDesign letterDesign;

        @NotBlank
        private String category;

        @NotNull
        private Category parentCategory;

        @NotBlank
        private List<@NotBlank String> infoNames;
    }

    @Getter
    public static class LetterUpdate {
        @Size(min = 1, max = 25)
        private String title;

        @Size(min = 1, max = 200)
        private String content;

        private LetterDesign letterDesign;

        @NotNull
        private String category;

        @NotNull
        private Category parentCategory;

        private List<@NotBlank String> infoNames;
    }
}
