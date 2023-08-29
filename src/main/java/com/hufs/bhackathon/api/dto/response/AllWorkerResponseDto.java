package com.hufs.bhackathon.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllWorkerResponseDto {
    private Long id;
    private String memo;
    private int clear;

    public static AllWorkerResponseDto of(Long id, String memo, int clear) {
        return AllWorkerResponseDto.builder()
                .id(id)
                .memo(memo)
                .clear(clear)
                .build();
    }
}
