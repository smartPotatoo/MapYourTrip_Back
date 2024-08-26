package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

@Data
public class UpdateScheduleTimeDto {
    private int id;  // 기존 SchedulesTimeEntity의 ID
    private String startTime;
    private String endTime;
    private String name;
    private String address;
    private String x;
    private String y;
}