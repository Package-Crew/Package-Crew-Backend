package com.hufs.bhackathon.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeliveryRequestDtoList {
    List<DeliveryRequestDto> deliveryRequestDtos;
}
