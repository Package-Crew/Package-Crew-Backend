package com.hufs.bhackathon.api.controller;

import com.hufs.bhackathon.api.dto.request.DeliveryRequestDto;
import com.hufs.bhackathon.api.dto.request.DeliveryRequestDtoList;
import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import com.hufs.bhackathon.api.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dangdol")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/work/{userId}")
    public ResponseEntity<Long> postWork(@PathVariable Long userId, @RequestBody WorkRequestDto workRequestDto) {
        Long result = deliveryService.postWork(userId, workRequestDto);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/work/{userId}/{workId}")
    public ResponseEntity<String> postDelivery(@PathVariable Long userId,
                                               @PathVariable Long workId,
                                               @RequestBody DeliveryRequestDtoList deliveryRequestDtoList) {
        String result = deliveryService.postDelivery(userId, workId, deliveryRequestDtoList.getDeliveryRequestDtos());
        return ResponseEntity.ok().body(result);
    }

}
