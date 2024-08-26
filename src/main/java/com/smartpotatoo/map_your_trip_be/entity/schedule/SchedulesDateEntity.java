package com.smartpotatoo.map_your_trip_be.entity.schedule;

import com.smartpotatoo.map_your_trip_be.entity.BaseEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules_date")
@EqualsAndHashCode(callSuper = true)  // 부모 클래스의 equals/hashCode를 호출
public class SchedulesDateEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schedules_id", referencedColumnName = "id", nullable = false)
    private SchedulesEntity schedule;

    @Column(nullable = false, length = 20)
    private String date;

    @Column(columnDefinition = "TEXT")
    private String content;

}
