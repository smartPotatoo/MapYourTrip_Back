package com.smartpotatoo.map_your_trip_be.domain.user.service;

import com.smartpotatoo.map_your_trip_be.common.error.ErrorCode;
import com.smartpotatoo.map_your_trip_be.common.exception.ApiException;
import com.smartpotatoo.map_your_trip_be.common.utils.JwtUtils;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.JoinRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginRequest;
import com.smartpotatoo.map_your_trip_be.domain.user.dto.LoginResponse;
import com.smartpotatoo.map_your_trip_be.domain.user.mapper.UserMapper;
import com.smartpotatoo.map_your_trip_be.entity.user.UserRepository;
import com.smartpotatoo.map_your_trip_be.entity.user.UsersEntity;
import com.smartpotatoo.map_your_trip_be.entity.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

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


}
