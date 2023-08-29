package com.hufs.bhackathon.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostWorkerResponseDto {
    private Long WorkId;
    private Long WorkerId;

    public static PostWorkerResponseDto of(Long WorkId, Long WorkerId) {
        return PostWorkerResponseDto.builder()
                .WorkId(WorkId)
                .WorkerId(WorkerId)
                .build();
    }
}
