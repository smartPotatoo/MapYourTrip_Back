package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateDetailedScheduleRequest {
    private List<UpdateScheduleDateDto> schedulesDateList;
}