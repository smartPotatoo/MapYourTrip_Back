package com.smartpotatoo.map_your_trip_be.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchedulesDateRepository extends JpaRepository<SchedulesDateEntity, Long> {
    Optional<SchedulesDateEntity> findByScheduleIdAndDate(Long schedulesId, String date);
}
