package com.hufs.bhackathon.api.dto.response;

import com.hufs.bhackathon.api.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashBoardDeliveryResponseDto {
    private Long trackingNum;
    private Integer done;
    private List<DashBoardItemResponseDto> items;

    public static DashBoardDeliveryResponseDto of(Long trackingNum, Integer done, List<Item> items) {
        return DashBoardDeliveryResponseDto.builder()
                .trackingNum(trackingNum)
                .done(done)
                .items(items.stream().map(i -> DashBoardItemResponseDto.builder()
                        .id(i.getId()).build()).collect(Collectors.toList()))
                .build();
    }
}
