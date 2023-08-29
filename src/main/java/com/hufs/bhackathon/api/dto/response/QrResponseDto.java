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
public class QrResponseDto {
    private Long trackingNum;
    private List<QrItemResponseDto> itemList;

    public static QrResponseDto of(Long trackingNum, List<Item> itemList) {
        return QrResponseDto.builder()
                .trackingNum(trackingNum)
                .itemList(itemList.stream().
                        map(i -> QrItemResponseDto.builder()
                                .id(i.getId())
                                .imageUrl(i.getImageUrl())
                                .itemName(i.getItemName()).build()).collect(Collectors.toList()))
                        .build();
    }
}
