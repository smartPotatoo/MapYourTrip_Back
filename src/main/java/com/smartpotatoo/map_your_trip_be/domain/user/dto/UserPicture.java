package com.smartpotatoo.map_your_trip_be.domain.user.dto;

import lombok.Data;

@Data
public class UserPicture {
    private String originalFilename;
    private String filePath;
    private String fileSize;
}
