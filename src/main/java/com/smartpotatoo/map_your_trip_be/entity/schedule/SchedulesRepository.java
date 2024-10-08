package com.smartpotatoo.map_your_trip_be.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<SchedulesEntity,Integer> {
    SchedulesEntity findById(int id);
    List<SchedulesEntity> findByUser_Username(String username);
}