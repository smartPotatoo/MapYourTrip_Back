package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.*;
import com.smartpotatoo.map_your_trip_be.domain.schedule.mapper.ScheduleMapper;
import com.smartpotatoo.map_your_trip_be.entity.schedule.*;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void addDetailedSchedule(AddDetailedScheduleRequest addDetailedScheduleRequest, String authorization, int scheduleId) {
        for (ScheduleDateDto dateDto : addDetailedScheduleRequest.getSchedulesDateList()) {
            dateDto.setSchedulesId(scheduleId);
            // 1. SchedulesDateEntity 저장
            SchedulesEntity schedulesEntity = schedulesRepository.findById(scheduleId);

            SchedulesDateEntity schedulesDateEntity = SchedulesDateEntity.builder()
                    .schedule(schedulesEntity)
                    .date(dateDto.getDate())
                    .content(dateDto.getContent())
                    .build();

            // 저장 후 ID가 생성된 SchedulesDateEntity
            schedulesDateRepository.save(schedulesDateEntity);

            // 2. SchedulesTimeEntity 저장
            for (ScheduleTimeDto timeDto : dateDto.getTimes()) {
                SchedulesTimeEntity schedulesTimeEntity = SchedulesTimeEntity.builder()
                        .schedulesDate(schedulesDateEntity)
                        .startTime(timeDto.getStartTime())
                        .endTime(timeDto.getEndTime())
                        .name(timeDto.getName())
                        .address(timeDto.getAddress())
                        .x(timeDto.getX())
                        .y(timeDto.getY())
                        .build();

                schedulesTimeRepository.save(schedulesTimeEntity);
            }
        }
    }

    @Override
    public ScheduleDetailInfoResponse getScheduleDetail(int scheduleId) {
        // 1. schedulesId로 SchedulesEntity 조회
        SchedulesEntity schedulesEntity = schedulesRepository.findById(scheduleId);

        // 2. schedulesId로 SchedulesDateEntity 조회
        List<SchedulesDateEntity> schedulesDateEntities = schedulesDateRepository.findByScheduleId(schedulesEntity.getId());

        // 3. 각 SchedulesDateEntity에 대한 SchedulesTimeEntity 조회
        List<ScheduleDateInfoResponse> scheduleDateResponses = schedulesDateEntities.stream()
                .map(dateEntity -> {
                    // SchedulesTimeRepository를 사용해 세부 일정을 조회
                    List<SchedulesTimeEntity> schedulesTimeEntities = schedulesTimeRepository.findBySchedulesDateId(dateEntity.getId());

                    List<ScheduleTimeInfoResponse> scheduleTimeResponses = schedulesTimeEntities.stream()
                            .map(timeEntity -> ScheduleTimeInfoResponse.builder()
                                    .startTime(timeEntity.getStartTime())
                                    .endTime(timeEntity.getEndTime())
                                    .name(timeEntity.getName())
                                    .address(timeEntity.getAddress())
                                    .x(timeEntity.getX())
                                    .y(timeEntity.getY())
                                    .build())
                            .collect(Collectors.toList());

                    return ScheduleDateInfoResponse.builder()
                            .schedulesId((long) scheduleId)
                            .date(dateEntity.getDate())
                            .content(dateEntity.getContent())
                            .times(scheduleTimeResponses)
                            .build();
                })

                .collect(Collectors.toList());

        // 4. 최종 DTO 반환
        return ScheduleDetailInfoResponse.builder()
                .id(schedulesEntity.getId())
                .nickname(schedulesEntity.getUser().getNickname())
                .tripName(schedulesEntity.getTripName())
                .address(schedulesEntity.getAddress())
                .startDate(schedulesEntity.getStartDate())
                .endDate(schedulesEntity.getEndDate())
                .schedulesDateList(scheduleDateResponses)
                .build();
    }

    @Override
    public void deleteSchedule(int schedulesId, String authorization) {
        //jwt에서 username 추출
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);

        //user 확인
        UsersEntity usersEntity = userRepository.findByUsername(username);
        SchedulesEntity schedulesEntity = schedulesRepository.findById(schedulesId);
        if(!usersEntity.getUsername().equals(schedulesEntity.getUser().getUsername())){
            throw new ApiException(ErrorCode.BAD_REQUEST,"삭제 권한이 없습니다.");
        }
        schedulesRepository.delete(schedulesEntity);

    }


}
