package com.smartpotatoo.map_your_trip_be.domain.user.mapper;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserMapper {
    public static UsersEntity toEntity(JoinRequest joinRequest){
        return Optional.ofNullable(joinRequest)
                .map((it)->{
                    return UsersEntity.builder()
                            .role(Role.ROLE_USER)
                            .username(joinRequest.getUsername())
                            .nickname(joinRequest.getNickname())
                            .password(joinRequest.getPassword())
                            .createdAt(LocalDateTime.now())
                            .build();

                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }
}
