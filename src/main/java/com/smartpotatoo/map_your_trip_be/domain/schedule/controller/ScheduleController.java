package com.smartpotatoo.map_your_trip_be.domain.schedule.controller;

import com.smartpotatoo.map_your_trip_be.common.api.Api;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddDetailedScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddScheduleRequest;
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
    public Api<String> addSchedule(
            @RequestBody AddScheduleRequest addScheduleRequest,
            @RequestHeader("Authorization") String authorization
    ){
        scheduleService.addSchedule(addScheduleRequest,authorization);
        return Api.OK("success");
    }

    @PostMapping("/schedule/detail")
    public Api<String> addDetailedSchedule(
            @RequestBody AddDetailedScheduleRequest addDetailedScheduleRequest,
            @RequestHeader("Authorization") String authorization
            ){
        scheduleService.addDetailedSchedule(addDetailedScheduleRequest, authorization);
        return Api.OK("success");
    }

    //일정 삭제
    @DeleteMapping("/schedule/{schedulesId}")
    public Api<String> deleteSchedule(
            @PathVariable("schedulesId") int schedulesId,
            @RequestHeader("Authorization") String authorization
    ){
        scheduleService.deleteSchedule(schedulesId,authorization);
        return Api.OK("success");
    }
}
