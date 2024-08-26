package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleTimeInfoResponse {
    private String startTime;
    private String endTime;
    private String name;
    private String address;
    private String x;
    private String y;
}