package com.smartpotatoo.map_your_trip_be.domain.user.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.UpdateProfileRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {
    private final UserService userService;

    // 프로필 업데이트(사진, 닉네임)
    @PutMapping("mypage")
    public Api<String> updateProfile(
            @RequestPart("data") UpdateProfileRequest updateProfileRequest,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authorization
    ) throws Exception {
            userService.updateProfile(updateProfileRequest, file, authorization);
            return Api.OK("Profile updated successfully");
    }
}
