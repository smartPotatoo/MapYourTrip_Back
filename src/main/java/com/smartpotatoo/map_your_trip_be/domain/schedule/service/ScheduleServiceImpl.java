package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;
import com.smartpotatoo.map_your_trip_be.domain.schedule.mapper.ScheduleMapper;
import com.smartpotatoo.map_your_trip_be.entity.schedule.*;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final SchedulesRepository schedulesRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final SchedulesDateRepository schedulesDateRepository;
    private final SchedulesTimeRepository schedulesTimeRepository;

    //일정 생성
    @Override
    public void addSchedule(AddScheduleRequest addScheduleRequest,String authorization) {
        //jwt에서 username 추출
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UsersEntity usersEntity = userRepository.findByUsername(username);
        SchedulesEntity schedulesEntity = ScheduleMapper.toEntity(addScheduleRequest,usersEntity);
        schedulesRepository.save(schedulesEntity);
    }

    @Override
    public List<ScheduleInfoResponse> scheduleInfoList() {
        List<SchedulesEntity> schedulesEntityList = schedulesRepository.findAll();
        List<ScheduleInfoResponse> scheduleInfoResponseList = schedulesEntityList.stream()
                .map(ScheduleMapper::toResponse).collect(Collectors.toList());
        return scheduleInfoResponseList;
    }

    @Override
    public void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization) {
        for (ScheduleDateDto dateDto : addDetailedScheduleRequest.getSchedulesDateList()) {
            SchedulesEntity schedulesEntity = schedulesRepository.findById(dateDto.getSchedulesId()).orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
            SchedulesDateEntity schedulesDateEntity = new SchedulesDateEntity();
            schedulesDateEntity.setSchedule(schedulesEntity);
            schedulesDateEntity.setDate(dateDto.getDate());
            schedulesDateEntity.setContent(dateDto.getContent());
            schedulesDateRepository.save(schedulesDateEntity);
        }

        for (ScheduleTimeDto timeDto : addDetailedScheduleRequest.getSchedulesTimeList()) {
            SchedulesDateEntity schedulesDateEntity = schedulesDateRepository.findById(timeDto.getSchedulesDateId()).orElseThrow(() -> new EntityNotFoundException("Schedule Date not found"));
            SchedulesTimeEntity schedulesTimeEntity = new SchedulesTimeEntity();
            schedulesTimeEntity.setSchedulesDate(schedulesDateEntity);
            schedulesTimeEntity.setStartTime(timeDto.getStartTime());
            schedulesTimeEntity.setEndTime(timeDto.getEndTime());
            schedulesTimeEntity.setName(timeDto.getName());
            schedulesTimeEntity.setAddress(timeDto.getAddress());
            schedulesTimeEntity.setX(timeDto.getX());
            schedulesTimeEntity.setY(timeDto.getY());
            schedulesTimeRepository.save(schedulesTimeEntity);
        }
    }
}
