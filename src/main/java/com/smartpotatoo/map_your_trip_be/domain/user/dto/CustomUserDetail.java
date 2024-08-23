package com.smartpotatoo.map_your_trip_be.domain.user.dto;

import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data

public class CustomUserDetail implements UserDetails {
    private UsersEntity usersEntity;
    public CustomUserDetail(UsersEntity usersEntity){
        this.usersEntity = usersEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(usersEntity.getRole().name()));
    }

    @Override
    public String getPassword() {
        return usersEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return usersEntity.getUsername();
    }
}
