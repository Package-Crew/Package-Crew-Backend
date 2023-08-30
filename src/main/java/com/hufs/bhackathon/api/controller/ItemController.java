package com.hufs.bhackathon.api.controller;

import com.hufs.bhackathon.api.dto.request.ItemRequestDto;
import com.hufs.bhackathon.api.dto.response.ItemResponseDto;
import com.hufs.bhackathon.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dangdol")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {

    private final ItemService itemService;


    @PostMapping("/item/{userId}/{itemName}")
    public ResponseEntity<String> postItem(
            @PathVariable Long userId,
            @PathVariable String itemName,
            @RequestPart MultipartFile image) {
        String result = itemService.postItem(userId, image, itemName);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/item/all/{userId}")
    public ResponseEntity<List<ItemResponseDto>> getItem(@PathVariable Long userId) {
        List<ItemResponseDto> result = itemService.getItem(userId);
        return ResponseEntity.ok().body(result);
    }
}
