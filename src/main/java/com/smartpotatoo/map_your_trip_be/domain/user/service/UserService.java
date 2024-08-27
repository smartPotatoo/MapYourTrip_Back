package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.domain.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //회원가입
    void join(JoinRequest joinRequest);
    //로그인
    LoginResponse login(LoginRequest loginRequest);
    // 프로필 수정(사진, 닉네임)
    void updateProfile(UpdateProfileRequest updateProfileRequest, MultipartFile file, String authorization) throws Exception;
    // 프로필 조회
    UserInfoResponse getProfile(String authorization);
}
