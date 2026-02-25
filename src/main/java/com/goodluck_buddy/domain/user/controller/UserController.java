package com.goodluck_buddy.domain.user.controller;

import com.goodluck_buddy.domain.user.dto.UserReqDto;
import com.goodluck_buddy.domain.user.dto.UserResDto;
import com.goodluck_buddy.domain.user.exception.code.UserSuccessCode;
import com.goodluck_buddy.domain.user.service.UserService;
import com.goodluck_buddy.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "회원정보 관련 API")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @PostMapping("/nickname")
    public ApiResponse<Void> checkNickname(@Valid @RequestBody UserReqDto.Nickname dto) {
        userService.checkNickname(dto.getNickname());
        return ApiResponse.onSuccess(UserSuccessCode.VALID_NICKNAME, null);
    }

    @PatchMapping("/profile")
    public ApiResponse<Void> updateProfile(
            @RequestHeader("Authorization") String accessToken,
            @Valid @RequestBody UserReqDto.Profile dto) {
        userService.updateProfile(accessToken, dto);
        return ApiResponse.onSuccess(UserSuccessCode.PROFILE_SAVE_OK, null);
    }

    @GetMapping("/me/profile")
    public ApiResponse<UserResDto.Profile> getMyProfile(@RequestHeader("Authorization") String accessToken) {
        UserResDto.Profile profile = userService.getMyProfile(accessToken);
        return ApiResponse.onSuccess(UserSuccessCode.PROFILE_GET_OK, profile);
    }

    @PatchMapping("/withdraw")
    public ApiResponse<Void> withdraw(@RequestHeader("Authorization") String accessToken) {
        userService.withdraw(accessToken);
        return ApiResponse.onSuccess(UserSuccessCode.WITHDRAW_OK, null);
    }

    @GetMapping("/{id}/profile")
    public ApiResponse<UserResDto.Profile> getMyProfile(
            @PathVariable Long id
    ) {
        UserResDto.Profile profile = userService.getUserProfile(id);
        return ApiResponse.onSuccess(UserSuccessCode.PROFILE_GET_OK, profile);
    }
}
