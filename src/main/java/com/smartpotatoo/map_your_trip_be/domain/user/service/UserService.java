package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginResponse;

public interface UserService {
    //회원가입
    void join(JoinRequest joinRequest);
    //로그인
    LoginResponse login(LoginRequest loginRequest);
}
