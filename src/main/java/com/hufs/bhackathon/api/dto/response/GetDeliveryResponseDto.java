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
public class GetDeliveryResponseDto {
    private String workName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    private List<DeliveryResponseDto> deliveryList;

    public static GetDeliveryResponseDto of(String workName, Date startDate, Date endDate, List<DeliveryResponseDto> deliveryList) {
        return GetDeliveryResponseDto.builder()
                .workName(workName)
                .startDate(startDate)
                .endDate(endDate)
                .deliveryList(deliveryList)
                .build();
    }
}
