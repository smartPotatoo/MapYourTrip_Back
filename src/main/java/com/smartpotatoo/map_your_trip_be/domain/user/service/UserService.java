package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;

public interface UserService {
    //회원가입
    void join(JoinRequest joinRequest);
}
