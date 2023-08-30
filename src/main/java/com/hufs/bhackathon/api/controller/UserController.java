package com.hufs.bhackathon.api.controller;

import com.hufs.bhackathon.api.dto.request.JoinDto;
import com.hufs.bhackathon.api.dto.request.LoginDto;
import com.hufs.bhackathon.api.dto.request.WorkerRequestDto;
import com.hufs.bhackathon.api.dto.response.LoginResponseDto;
import com.hufs.bhackathon.api.dto.response.PostWorkerResponseDto;
import com.hufs.bhackathon.api.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

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

    @PostMapping("/dangdol/worker/{workId}")
    public ResponseEntity<PostWorkerResponseDto> postWorker(@PathVariable Long workId, @RequestBody WorkerRequestDto workerRequestDto) {
        PostWorkerResponseDto postWorkerResponseDto = usersService.postWorker(workId, workerRequestDto);
        return ResponseEntity.ok().body(postWorkerResponseDto);
    }
}
