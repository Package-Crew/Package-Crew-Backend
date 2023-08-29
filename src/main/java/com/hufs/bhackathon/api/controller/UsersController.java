package com.hufs.bhackathon.api.controller;

import com.hufs.bhackathon.api.dto.request.JoinDto;
import com.hufs.bhackathon.api.dto.request.LoginDto;
import com.hufs.bhackathon.api.dto.response.LoginResponseDto;
import com.hufs.bhackathon.api.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto) {
        return ResponseEntity.ok().body(usersService.join(joinDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        LoginResponseDto loginResponseDto = usersService.login(loginDto);
        return ResponseEntity.ok().body(loginResponseDto);
    }
}
