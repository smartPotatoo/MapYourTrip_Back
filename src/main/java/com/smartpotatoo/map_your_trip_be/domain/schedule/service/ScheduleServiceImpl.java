package com.smartpotatoo.map_your_trip_be.domain.schedule.service;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.AddScheduleRequest;
import com.smartpotatoo.map_your_trip_be.domain.schedule.dto.ScheduleInfoResponse;
import com.smartpotatoo.map_your_trip_be.domain.schedule.mapper.ScheduleMapper;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesEntity;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
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
