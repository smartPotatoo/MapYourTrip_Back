package com.smartpotatoo.map_your_trip_be.domain.schedule.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesDateEntity;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesEntity;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesTimeEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ScheduleMapper {
    //AddScheduleRequest to SchedulesEntity
    public static SchedulesEntity toEntity(AddScheduleRequest addScheduleRequest, UsersEntity usersEntity){
        return Optional.ofNullable(addScheduleRequest)
                .map(it->{
                    return SchedulesEntity.builder()
                            .user(usersEntity)
                            .tripName(addScheduleRequest.getTripName())
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
                            .tripName(schedulesEntity.getTripName())
                            .address(schedulesEntity.getAddress())
                            .nickname(schedulesEntity.getUser().getNickname())
                            .startDate(schedulesEntity.getStartDate())
                            .endDate(schedulesEntity.getEndDate())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }

    //UpdateScheduleRequest to SchedulesEntity
    public static SchedulesEntity toEntity(UpdateScheduleRequest updateScheduleRequest, UsersEntity usersEntity){
        return Optional.ofNullable(updateScheduleRequest)
                .map(it->{
                    return SchedulesEntity.builder()
                            .user(usersEntity)
                            .tripName(updateScheduleRequest.getTripName())
                            .address(updateScheduleRequest.getAddress())
                            .endDate(updateScheduleRequest.getEndDate())
                            .startDate(updateScheduleRequest.getStartDate())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }

    public static MapAddressResponse toResponse(ResponseEntity<Map> entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Map> list = objectMapper.convertValue(Objects.requireNonNull(entity.getBody()).get("addresses"), ArrayList.class);
        String[] address = list.get(0).get("roadAddress").toString().split(" ");
        return Optional.of(list)
                .map(it -> {
                    return MapAddressResponse.builder()
                            .y(list.get(0).get("y").toString())
                            .x(list.get(0).get("x").toString())
                            .ctprvnNm(address.length != 1 ? address[0] : null)
                            .sggNm(address.length != 1 ? address[1] : null)
                            .englishAddress(list.get(0).get("englishAddress").toString())
                            .dtlAdres(list.get(0).get("roadAddress").toString())
                            .jibunAddress(list.get(0).get("jibunAddress").toString())
                            .build();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
