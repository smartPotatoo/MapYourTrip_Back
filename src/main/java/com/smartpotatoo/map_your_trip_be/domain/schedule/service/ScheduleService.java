package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;

import java.util.List;

public interface ScheduleService {
    //일정 생성
    void addSchedule(AddScheduleRequest addScheduleRequest,String authorization);
    List<ScheduleInfoResponse> scheduleInfoList();

    //일정 삭제
    void deleteSchedule(int schedulesId,String authorization);
    void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization, int scheduleId);
    ScheduleDetailInfoResponse getScheduleDetail(int scheduleId);

    // 세부 일정 수정
    void updateDetailedSchedule(UpdateDetailedScheduleRequest updateDetailedScheduleRequest, String authorization, int scheduleId);
}
