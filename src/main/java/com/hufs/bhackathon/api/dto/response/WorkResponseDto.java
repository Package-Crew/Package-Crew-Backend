package com.hufs.bhackathon.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkResponseDto {

    private Long id;
    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    private int total;
    private int clear;
    private int avg;
    private int workers;

    public static WorkResponseDto of(Long id, String workName, Date startDate, Date endDate, int workers, int total, int clear, int avg) {
        return WorkResponseDto.builder()
                .id(id)
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .workers(workers)
                .total(total)
                .clear(clear)
                .avg(avg)
                .build();
    }
}
