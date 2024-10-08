package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.common.utils.FileUtils;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.schedule.mapper.ScheduleMapper;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.*;
import com.smartpotatoo.map_your_trip_be.domain.user.mapper.UserMapper;
import com.smartpotatoo.map_your_trip_be.domain.user.mapper.UserPictureMapper;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesEntity;
import com.smartpotatoo.map_your_trip_be.entity.schedule.SchedulesRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UserPictureEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.UserPictureRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserPictureRepository userPictureRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final FileUtils fileUtils;
    private final SchedulesRepository schedulesRepository;

    //회원가입
    public void join(JoinRequest joinRequest){

        // Check if username already exists
        if (userRepository.existsByUsername(joinRequest.getUsername())) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 존재하는 ID입니다.");
        }

        // Check if nickname already exists
        if (userRepository.existsByNickname(joinRequest.getNickname())) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 존재하는 닉네임입니다.");
        }

        UsersEntity entity = UserMapper.toEntity(joinRequest);
        entity.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        userRepository.save(entity);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        //유저 유무 확인
        UsersEntity usersEntity = Optional.ofNullable(userRepository.findByUsernameAndRole(loginRequest.getUsername(), Role.ROLE_USER))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"유저 정보가 없습니다."));
        //바말번호 확인
        boolean passwordMatch = bCryptPasswordEncoder.matches(loginRequest.getPassword(), usersEntity.getPassword());
        if(!passwordMatch){
            throw new ApiException(ErrorCode.BAD_REQUEST,"비밀번호가 틀렸습니다.");
        }
        LoginResponse loginResponse = UserMapper.toResponse(usersEntity);
        loginResponse.setToken(jwtUtils.generateToken(usersEntity));
        return loginResponse;
    }

    @Override
    public void updateProfile(UpdateProfileRequest updateProfileRequest, MultipartFile profileImage, String authorization) throws Exception {
        // 유저 정보 추출
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UsersEntity user = userRepository.findByUsername(username);

        // 닉네임 수정
        if (updateProfileRequest.getNickname() != null) {
            user.setNickname(updateProfileRequest.getNickname());
        }

        // DB에 유저 정보 업데이트
        userRepository.save(user);

        // 프로필 이미지 파일 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            String storedFilePath = fileUtils.storeFile(profileImage, user.getNickname());

            // 파일 정보 저장
            UserPictureEntity userPicture = userPictureRepository.findByUserId(user.getId())
                    .orElse(new UserPictureEntity());

            // 이미 파일이 있는 경우 삭제
            if(userPicture.getFilePath() != null){
                fileUtils.deleteFile(userPicture.getFilePath());
            }

            userPicture.setUser(user);
            userPicture.setOriginalFileName(profileImage.getOriginalFilename());
            userPicture.setFilePath(storedFilePath);
            userPicture.setFileSize("" + profileImage.getSize());
            userPicture.setCreatedAt(LocalDateTime.now());
            userPictureRepository.save(userPicture);
        }
    }

    @Override
    public UserInfoResponse getProfile(String authorization) {
        // 유저 정보 추출
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UsersEntity user = userRepository.findByUsername(username);

        // 닉네임 조회
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setNickname(user.getNickname());

        // 프로필 사진 조회
        UserPictureEntity userPictureEntity = userPictureRepository.findByUserId(user.getId()).orElse(new UserPictureEntity());
        if(userPictureEntity.getUser() != null) {
            userInfoResponse.setUserpicture(UserPictureMapper.toDto(userPictureEntity));
        }

        // 작성한 일정 목록 조회
        List<SchedulesEntity> schedulesEntityList = schedulesRepository.findByUser_Username(username);
        userInfoResponse.setScheduleInfoResponse(schedulesEntityList.stream()
                .map(ScheduleMapper::toResponse).collect(Collectors.toList()));

        return userInfoResponse;
    }
}
