package com.smartpotatoo.map_your_trip_be.domain.schedule.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;
import com.smartpotatoo.map_your_trip_be.domain.schedule.service.ScheduleService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping("/schedule")
    public Api<ScheduleInfoResponse> addSchedule(
            @RequestBody AddScheduleRequest addScheduleRequest,
            @RequestHeader("Authorization") String authorization
    ){
        ScheduleInfoResponse scheduleInfoResponse = scheduleService.addSchedule(addScheduleRequest,authorization);
        return Api.OK(scheduleInfoResponse);
    }

    // 일정 수정
    @PutMapping("/schedule/{scheduleId}")
    public Api<String> updateSchedule(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("scheduleId") int schedulesId,
            @RequestBody UpdateScheduleRequest updateScheduleRequest
    ){
        scheduleService.updateSchedule(updateScheduleRequest, schedulesId, authorization);
        return Api.OK("success");
    }

    //일정 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    public Api<String> deleteSchedule(
            @PathVariable("scheduleId") int schedulesId,
            @RequestHeader("Authorization") String authorization
    ){
        scheduleService.deleteSchedule(schedulesId,authorization);
        return Api.OK("success");
    }

    // 세부 일정 생성
    @PostMapping("/schedule/{scheduleId}/detail")
    public Api<String> addDetailedSchedule(
            @RequestBody AddDetailedScheduleRequest addDetailedScheduleRequest,
            @RequestHeader("Authorization") String authorization,
            @PathVariable("scheduleId") int scheduleId
            ){
        scheduleService.addDetailedSchedule(addDetailedScheduleRequest, authorization, scheduleId);
        return Api.OK("success");
    }

    // 세부 일정 수정
    @PutMapping("/schedule/{scheduleId}/detail")
    public Api<String> updateDetailedSchedule(
            @RequestBody UpdateDetailedScheduleRequest updateDetailedScheduleRequest,
            @RequestHeader("Authorization") String authorization,
            @PathVariable("scheduleId") int scheduleId
    ) {
        scheduleService.updateDetailedSchedule(updateDetailedScheduleRequest, authorization, scheduleId);
        return Api.OK("success");
    }
}
