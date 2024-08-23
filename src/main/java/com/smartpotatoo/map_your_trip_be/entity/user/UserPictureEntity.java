package com.smartpotatoo.map_your_trip_be.entity.user;

import com.smartpotatoo.map_your_trip_be.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_picture")
public class UserPictureEntity extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username", nullable = false)
    private UsersEntity user;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
