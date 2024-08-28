package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;

import java.util.List;

public interface ScheduleService {
    //일정 생성
    ScheduleInfoResponse addSchedule(AddScheduleRequest addScheduleRequest,String authorization);
    // 일정 조회
    List<ScheduleInfoResponse> scheduleInfoList();
    // 일정 수정
    void updateSchedule(UpdateScheduleRequest updateScheduleRequest, int schedulesId, String authorization);
    //일정 삭제
    void deleteSchedule(int schedulesId,String authorization);
    // 세부 일정 생성
    void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization, int scheduleId);
    // 세부 일정 조회
    ScheduleDetailInfoResponse getScheduleDetail(int scheduleId);
    // 세부 일정 수정
    void updateDetailedSchedule(UpdateDetailedScheduleRequest updateDetailedScheduleRequest, String authorization, int scheduleId);
}
