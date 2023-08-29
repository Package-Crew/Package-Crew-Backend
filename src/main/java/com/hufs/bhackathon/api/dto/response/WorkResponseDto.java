package com.hufs.bhackathon.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkResponseDto {

    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    private int total;
    private int clear;
    private int avg;

    public static WorkResponseDto of(String workName, Date startDate, Date endDate, int total, int clear, int avg) {
        return WorkResponseDto.builder()
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .total(total)
                .clear(clear)
                .avg(avg)
                .build();
    }
}
