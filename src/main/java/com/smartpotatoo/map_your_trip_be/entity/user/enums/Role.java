package com.smartpotatoo.map_your_trip_be.entity.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ;

    private final String description;
}