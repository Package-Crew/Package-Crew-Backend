package com.hufs.bhackathon.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageWorkerResponseDto {
    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    private List<AllWorkerResponseDto> allWorkerList;

    public static ManageWorkerResponseDto of(String workName, Date startDate, Date endDate, List<AllWorkerResponseDto> allWorkerList) {
        return ManageWorkerResponseDto.builder()
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .allWorkerList(allWorkerList)
                .build();
    }
}
