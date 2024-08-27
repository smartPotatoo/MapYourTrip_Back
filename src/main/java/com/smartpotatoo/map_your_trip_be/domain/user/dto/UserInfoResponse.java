package com.smartpotatoo.map_your_trip_be.domain.user.dto;

import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleInfoResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    private String nickname;
    UserPicture userpicture;
    List<ScheduleInfoResponse> scheduleInfoResponse;
}
