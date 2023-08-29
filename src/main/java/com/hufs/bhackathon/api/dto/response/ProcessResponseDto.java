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
public class ProcessResponseDto {
    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;
    private int total;
    private int clear;
    private long limit; //남은 시간
    private int avg; // 전체 퍼센트
    private int my; // 내가 작업한 개수
    private int myPercent; // 내가 작업한 퍼센트
    private int myTotal; // 앞으로 할 것 같은 양

    public static ProcessResponseDto of(String workName, Date startDate, Date endDate, int total, int clear,int avg,  long limit, int my, int myPercent, int myTotal) {
        return ProcessResponseDto.builder()
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .total(total)
                .clear(clear)
                .limit(limit)
                .avg(avg)
                .my(my)
                .myPercent(myPercent)
                .myTotal(myTotal)
                .build();
    }
}