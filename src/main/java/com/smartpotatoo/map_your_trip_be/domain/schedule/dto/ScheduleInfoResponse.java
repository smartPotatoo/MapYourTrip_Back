package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleInfoResponse {
    public int id;
    public String nickname;
    public String address;
    public String startDate;
    public String endDate;
}
