package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

@Data
public class ScheduleTimeDto {
    private String startTime;
    private String endTime;
    private String name;
    private String address;
    private String x;
    private String y;
}