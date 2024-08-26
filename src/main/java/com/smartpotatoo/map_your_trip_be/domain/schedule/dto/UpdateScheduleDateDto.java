package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateScheduleDateDto {
    private Integer id;  // 기존 SchedulesDateEntity의 ID
    private String date;
    private String content;
    private List<UpdateScheduleTimeDto> times;
}