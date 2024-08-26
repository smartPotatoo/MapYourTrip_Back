package com.smartpotatoo.map_your_trip_be.entity.schedule;

import com.smartpotatoo.map_your_trip_be.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules_time")
public class SchedulesTimeEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schedules_date_id", referencedColumnName = "id", nullable = false)
    private SchedulesDateEntity schedulesDate;

    @Column(name = "start_time", nullable = false, length = 20)
    private String startTime;

    @Column(name = "end_time", nullable = false, length = 20)
    private String endTime;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 200)
    private String x;

    @Column(nullable = false, length = 200)
    private String y;
}
