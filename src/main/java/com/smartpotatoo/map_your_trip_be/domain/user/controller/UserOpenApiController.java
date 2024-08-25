package com.smartpotatoo.map_your_trip_be.domain.user.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginResponse;
import com.smartpotatoo.map_your_trip_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//jwt가 필요없는 api의 경우 jwtRequestFilter에서 url을 계속 추가해주면서 예외처리를 해줘해서 앞에 open-api를 붙여 추가적인 작업이 없게 만들었습니다
@RequestMapping("/open-api")
@Slf4j
public class UserOpenApiController {
    private final UserService userService;
    @PostMapping("/join")
    public Api<String> join(
            @RequestBody JoinRequest joinRequest
    ){
        userService.join(joinRequest);
        return Api.OK("create");
    }

    @PostMapping("/login")
    public Api<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ){
        LoginResponse loginResponse = userService.login(loginRequest);
        return Api.OK(loginResponse);
    }
}
