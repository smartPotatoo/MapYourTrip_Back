package com.smartpotatoo.map_your_trip_be.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPictureRepository extends JpaRepository<UserPictureEntity, Integer> {
    Optional<UserPictureEntity> findByUserId(int userId);
}
