package com.hufs.bhackathon.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrItemResponseDto {
    private Long id;
    private String imageUrl;
    private String itemName;
}
