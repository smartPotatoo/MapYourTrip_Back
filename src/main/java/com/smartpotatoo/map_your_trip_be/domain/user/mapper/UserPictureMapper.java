package com.smartpotatoo.map_your_trip_be.domain.user.mapper;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginResponse;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.UserInfoResponse;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.UserPicture;
import com.smartpotatoo.map_your_trip_be.entity.user.UserPictureEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserPictureMapper {
    public static UserPicture toDto(UserPictureEntity userPictureEntity){
        return Optional.ofNullable(userPictureEntity)
                .map((it)->{
                    return UserPicture.builder()
                            .originalFilename(userPictureEntity.getOriginalFileName())
                            .filePath(userPictureEntity.getFilePath())
                            .fileSize(userPictureEntity.getFileSize())
                            .build();

                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }
}
