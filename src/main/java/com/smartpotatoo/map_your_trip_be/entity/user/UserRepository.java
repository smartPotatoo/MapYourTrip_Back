package com.smartpotatoo.map_your_trip_be.entity.user;

import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity,Integer> {
    UsersEntity findByUsername(String username);
    UsersEntity findByUsernameAndRole(String username, Role role);
}