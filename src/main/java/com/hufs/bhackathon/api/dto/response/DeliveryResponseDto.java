package com.hufs.bhackathon.api.dto.response;

import com.hufs.bhackathon.api.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDto {
    private Long trackingNum;
    private Integer done;
    private Long workerId;
    private List<DashBoardItemResponseDto> items;

    public static DeliveryResponseDto of(Long trackingNum, Integer done, Long workerId, List<Item> items) {
        return DeliveryResponseDto.builder()
                .trackingNum(trackingNum)
                .done(done)
                .workerId(workerId)
                .items(items.stream().map(i -> DashBoardItemResponseDto.builder()
                        .id(i.getId()).build()).collect(Collectors.toList()))
                .build();
    }
}
