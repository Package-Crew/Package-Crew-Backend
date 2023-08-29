package com.hufs.bhackathon.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {
    private Long id;
    private String itemName;
    private String imageUrl;

    public static ItemResponseDto of(Long id, String itemName, String imageUrl) {
        return ItemResponseDto.builder()
                .id(id)
                .itemName(itemName)
                .imageUrl(imageUrl)
                .build();
    }
}
