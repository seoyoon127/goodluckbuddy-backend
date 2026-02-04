package com.goodluck_buddy.domain.user.dto;

import com.goodluck_buddy.domain.user.enums.Category;
import com.goodluck_buddy.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class UserResDto {

    @Getter
    @Builder
    public static class Profile {
        private String nickname;
        private Gender gender;
        private LocalDate birth;
        private Category category;
    }
}
