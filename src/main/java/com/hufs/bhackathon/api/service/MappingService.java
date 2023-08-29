package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.Delivery;
import com.hufs.bhackathon.api.domain.entity.Item;
import com.hufs.bhackathon.api.domain.entity.Mapping;
import com.hufs.bhackathon.api.domain.repository.DeliveryRepository;
import com.hufs.bhackathon.api.domain.repository.ItemRepository;
import com.hufs.bhackathon.api.domain.repository.MappingRepository;
import com.hufs.bhackathon.api.dto.request.DeliveryRequestDto;
import com.hufs.bhackathon.api.dto.request.ItemIdRequestDto;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingService {

    private final MappingRepository mappingRepository;
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public void mapToDeliveryAndItem(DeliveryRequestDto deliveryDto, List<ItemIdRequestDto> items) {
        for (ItemIdRequestDto item : items) {
            Delivery delivery = deliveryRepository.findByTrackingNum(deliveryDto.getTrackingNum()).orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
            Item checkItem = itemRepository.findById(item.getId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            mappingRepository.save(Mapping.of(checkItem, delivery));
        }
    }
}
