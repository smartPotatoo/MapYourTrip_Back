package com.smartpotatoo.map_your_trip_be.entity.user;

import com.smartpotatoo.map_your_trip_be.entity.BaseEntity;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UsersEntity extends BaseEntity {
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<UserPictureEntity> pictures;

    @OneToMany(mappedBy = "user")
    private List<SchedulesEntity> schedules;
}
