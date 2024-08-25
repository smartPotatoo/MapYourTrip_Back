package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

@Data
public class AddScheduleRequest {
    public String address;
    public String startDate;
    public String endDate;
}
