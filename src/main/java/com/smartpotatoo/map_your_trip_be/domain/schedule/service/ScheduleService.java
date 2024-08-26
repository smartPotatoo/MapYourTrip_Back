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

    //일정 삭제
    void deleteSchedule(int schedulesId,String authorization);
    void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization, int scheduleId);
    ScheduleDetailInfoResponse getScheduleDetail(int scheduleId);
}
