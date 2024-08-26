package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleDetailInfoResponse {
    private int id;  // schedulesId
    private String nickname;
    private String tripName;
    private String address;
    private String startDate;
    private String endDate;
    private List<ScheduleDateInfoResponse> schedulesDateList;
}