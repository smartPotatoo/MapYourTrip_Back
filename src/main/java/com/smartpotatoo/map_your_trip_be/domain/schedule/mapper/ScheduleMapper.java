package com.smartpotatoo.map_your_trip_be.domain.schedule.mapper;


import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleInfoResponse;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;

import java.util.Optional;

public class ScheduleMapper {
    //AddScheduleRequest to SchedulesEntity
    public static SchedulesEntity toEntity(AddScheduleRequest addScheduleRequest, UsersEntity usersEntity){
        return Optional.ofNullable(addScheduleRequest)
                .map(it->{
                    return SchedulesEntity.builder()
                            .user(usersEntity)
                            .address(addScheduleRequest.getAddress())
                            .endDate(addScheduleRequest.getEndDate())
                            .startDate(addScheduleRequest.getStartDate())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }

    //SchedulesEntity to ScheduleInfoResponse
    public static ScheduleInfoResponse toResponse(SchedulesEntity schedulesEntity){
        return Optional.ofNullable(schedulesEntity)
                .map(it->{
                    return ScheduleInfoResponse.builder()
                            .id(schedulesEntity.getId())
                            .address(schedulesEntity.getAddress())
                            .nickname(schedulesEntity.getUser().getNickname())
                            .startDate(schedulesEntity.getStartDate())
                            .endDate(schedulesEntity.getEndDate())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }
}
