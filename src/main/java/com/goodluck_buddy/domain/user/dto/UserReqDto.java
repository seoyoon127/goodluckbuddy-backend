package com.goodluck_buddy.domain.user.dto;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.user.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

public class UserReqDto {

    @Getter
    public static class Nickname {
        @NotBlank
        private String nickname;
    }

    @Getter
    public static class Profile {
        private String nickname;

        private Gender gender;

        private LocalDate birth;

        private Category category;
    }
}
