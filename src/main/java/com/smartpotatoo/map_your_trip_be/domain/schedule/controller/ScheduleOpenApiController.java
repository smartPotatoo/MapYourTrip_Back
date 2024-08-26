package com.smartpotatoo.map_your_trip_be.domain.schedule.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleInfoResponse;
import com.smartpotatoo.map_your_trip_be.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
//jwt가 필요없는 api의 경우 jwtRequestFilter에서 url을 계속 추가해주면서 예외처리를 해줘야 해서 앞에 open-api를 붙여 추가적인 작업이 없게 만들었습니다
@RequestMapping("/open-api")
@Slf4j
public class ScheduleOpenApiController {
    private final ScheduleService scheduleService;
    //일정 목록 조회
    @GetMapping("/")
    public Api<List<ScheduleInfoResponse>> scheduleInfoList(){
        List<ScheduleInfoResponse> scheduleInfoResponseList = scheduleService.scheduleInfoList();
        return Api.OK(scheduleInfoResponseList);
    }

    @GetMapping("/{scheduleId}")
    public Api<List<ScheduleInfoResponse>> detailedScheduleInfoList(){
        List<ScheduleInfoResponse> scheduleInfoResponseList = scheduleService.scheduleInfoList();
        return Api.OK(scheduleInfoResponseList);
    }
}
