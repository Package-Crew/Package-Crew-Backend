package com.hufs.bhackathon.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardResponseDto {
    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    private int total;
    private int clear;
    private long limit;
    private int avg;

    private List<DashBoardDeliveryResponseDto> deliveryList; //7개만 보여주기

    public static DashBoardResponseDto of(String workName, Date startDate, Date endDate, int total, int clear,int avg,  long limit, List<DashBoardDeliveryResponseDto> deliveryList) {
        return DashBoardResponseDto.builder()
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .total(total)
                .clear(clear)
                .limit(limit)
                .avg(avg)
                .deliveryList(deliveryList)
                .build();
    }
}
