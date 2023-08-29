package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.Users;
import com.hufs.bhackathon.api.domain.repository.UsersRepository;
import com.hufs.bhackathon.api.dto.request.JoinDto;
import com.hufs.bhackathon.api.dto.request.LoginDto;
import com.hufs.bhackathon.api.dto.response.LoginResponseDto;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;


    @Transactional
    public String join(JoinDto joinDto) {
        Users checkUser = usersRepository.findByEmail(joinDto.getEmail());
        if(checkUser != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }
        usersRepository.save(Users.of(joinDto));
        return joinDto.getEmail() + "님이 회원가입되었습니다.";
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginDto loginDto) {
        Users user = usersRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return LoginResponseDto.of(user.getId());
    }
}
