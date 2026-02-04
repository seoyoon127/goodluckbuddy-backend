package com.goodluck_buddy.domain.user.controller;

import com.goodluck_buddy.domain.user.dto.UserReqDto;
import com.goodluck_buddy.domain.user.dto.UserResDto;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserControllerDocs {

    @Operation(
            summary = "닉네임 중복확인",
            description = "해당 닉네임이 사용중인지 확인합니다."
    )
    ApiResponse<Void> checkNickname(@Valid @RequestBody UserReqDto.Nickname dto);

    @Operation(
            summary = "회원정보 입력/수정",
            description = "회원 정보를 저장/갱신합니다."
    )
    ApiResponse<Void> updateProfile(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody UserReqDto.Profile dto);

    @Operation(
            summary = "내 프로필 조회",
            description = "내 회원 정보를 조회합니다."
    )
    ApiResponse<UserResDto.Profile> getMyProfile(@RequestHeader("Authorization") String accessToken);
}
