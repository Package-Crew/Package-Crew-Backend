package com.hufs.bhackathon.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private Long UserId;

    @Builder
    public LoginResponseDto(Long UserId) {
        this.UserId = UserId;
    }

    public static LoginResponseDto of(Long userId) {
        return LoginResponseDto.builder()
                .UserId(userId)
                .build();
    }
}
