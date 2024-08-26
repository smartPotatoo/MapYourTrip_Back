package com.smartpotatoo.map_your_trip_be.entity.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulesTimeRepository extends JpaRepository<SchedulesTimeEntity, Long> {
    
}
