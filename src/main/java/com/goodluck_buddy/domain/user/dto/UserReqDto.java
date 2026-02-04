package com.goodluck_buddy.domain.user.dto;

import com.goodluck_buddy.domain.user.enums.Category;
import com.goodluck_buddy.domain.user.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        @NotBlank
        private String nickname;

        @NotNull
        private Gender gender;

        @NotNull
        private LocalDate birth;

        @NotNull
        private Category category;
    }
}
