package com.smartpotatoo.map_your_trip_be.entity.schedule;

import com.smartpotatoo.map_your_trip_be.entity.BaseEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
public class SchedulesEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username", nullable = false)
    private UsersEntity user;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(name = "start_date", nullable = false, length = 20)
    private String startDate;

    @Column(name = "end_date", nullable = false, length = 20)
    private String endDate;

}
