package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.Data;

@Data
public class ScheduleDateDto {
    private Long schedulesId;
    private String date;
    private String content;
}