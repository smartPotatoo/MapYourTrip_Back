package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.common.utils.FileUtils;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginResponse;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.UpdateProfileRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.mapper.UserMapper;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserPictureRepository userPictureRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final FileUtils fileUtils;

    //회원가입
    public void join(JoinRequest joinRequest){
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
        user.setNickname(updateProfileRequest.getNickname());

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
}
