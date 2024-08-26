package com.smartpotatoo.map_your_trip_be.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulesTimeRepository extends JpaRepository<SchedulesTimeEntity, Long> {

    List<SchedulesTimeEntity> findBySchedulesDateId(Long schedulesDateId);
}
