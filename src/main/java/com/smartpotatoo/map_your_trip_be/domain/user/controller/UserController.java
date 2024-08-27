package com.smartpotatoo.map_your_trip_be.domain.user.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.UpdateProfileRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.service.UserService;
import com.smartpotatoo.map_your_trip_be.entity.user.UserPictureEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UserPictureRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserPictureRepository userPictureRepository;

    // 프로필 업데이트(사진, 닉네임)
    @PutMapping("/mypage")
    public Api<String> updateProfile(
            @RequestPart("data") UpdateProfileRequest updateProfileRequest,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authorization
    ) throws Exception {
            userService.updateProfile(updateProfileRequest, file, authorization);
            return Api.OK("Profile updated successfully");
    }

    // 프로필 사진을 웹에서 보기 위한 다운로드
    @GetMapping("/mypage/picture")
    public void downloadUserPicture(
            @RequestHeader("Authorization") String authorization,
            HttpServletResponse response
    ) throws Exception {
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UsersEntity user = userRepository.findByUsername(username);
        UserPictureEntity userPicture = userPictureRepository.findByUserId(user.getId())
                .orElse(new UserPictureEntity());

        if(userPicture.getFilePath() == null) {
            return;
        }

        Path path = Paths.get(userPicture.getFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("apllication/octect-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(userPicture.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
