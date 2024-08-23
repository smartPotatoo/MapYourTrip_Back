package com.smartpotatoo.map_your_trip_be.domain.user.dto;

import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponse {
    public String nickname;
    public String username;
    public String token;
    public LocalDateTime createdAt;
    public Role role;
}
