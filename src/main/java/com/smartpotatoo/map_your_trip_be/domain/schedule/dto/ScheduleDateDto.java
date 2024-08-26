package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleDateDto {
    private Long schedulesId;
    private String date;
    private String content;
    private List<ScheduleTimeDto> times;  // 세부 일정을 위한 리스트
}