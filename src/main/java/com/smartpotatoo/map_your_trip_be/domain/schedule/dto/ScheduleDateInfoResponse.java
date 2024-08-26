package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleDateInfoResponse {
    private Long schedulesId;

    private String date;
    private String content;
    private List<ScheduleTimeInfoResponse> times;  // 세부 일정 리스트
}