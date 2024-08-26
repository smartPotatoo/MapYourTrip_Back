package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddDetailedScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleDetailInfoResponse;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleInfoResponse;

import java.util.List;

public interface ScheduleService {
    //일정 생성
    void addSchedule(AddScheduleRequest addScheduleRequest,String authorization);
    List<ScheduleInfoResponse> scheduleInfoList();
    void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization);
    ScheduleDetailInfoResponse getScheduleDetail(int scheduleId);
}
