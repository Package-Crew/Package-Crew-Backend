package com.hufs.bhackathon.api.controller;

import com.hufs.bhackathon.api.dto.request.DeliveryRequestDtoList;
import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import com.hufs.bhackathon.api.dto.response.*;
import com.hufs.bhackathon.api.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dangdol")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/qrscan")
    public ResponseEntity<QrResponseDto> qrScan(@RequestParam Long trackingNum) {
        QrResponseDto result = deliveryService.qrScan(trackingNum);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 알바생 등록하고 나서 패키징 마저 하기
     */
    @PostMapping("/packaging/{trackingNum}/{workerId}")
    public ResponseEntity<String> packaging(@PathVariable Long trackingNum,
                                            @PathVariable Long workerId,
                                            @RequestPart MultipartFile video) {
        String result = deliveryService.packaging(trackingNum, workerId, video);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/now/{userId}")
    public ResponseEntity<List<WorkResponseDto>> nowWork(@PathVariable Long userId) {
        List<WorkResponseDto> result = deliveryService.nowWork(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/previous/{userId}")
    public ResponseEntity<List<WorkResponseDto>> previousWork(@PathVariable Long userId) {
        List<WorkResponseDto> result = deliveryService.previousWork(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/dashboard/{workId}")
    public ResponseEntity<DashBoardResponseDto> dashboard(@PathVariable Long workId) {
        DashBoardResponseDto result = deliveryService.dashboard(workId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/delivery/{workId}")
    public ResponseEntity<GetDeliveryResponseDto> getDelivery(@PathVariable Long workId) {
        GetDeliveryResponseDto result = deliveryService.getDelivery(workId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/delivery/all/{workerId}")
    public ResponseEntity<List<DashBoardDeliveryResponseDto>> getDeliveryAll(@PathVariable Long workerId) {
        List<DashBoardDeliveryResponseDto> result = deliveryService.getDeliveryAll(workerId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/worker/all/{workId}")
    public ResponseEntity<ManageWorkerResponseDto> getWorkerAll(@PathVariable Long workId) {
        ManageWorkerResponseDto result = deliveryService.getWorkerAll(workId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/process")
    public ResponseEntity<GetDeliveryResponseDto> getProcess(@RequestParam Long workerId) {
        GetDeliveryResponseDto result = deliveryService.getProcess(workerId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/process/all")
    public ResponseEntity<ProcessResponseDto> getAllProcess(@RequestParam Long workerId) {
        ProcessResponseDto result = deliveryService.getAllProcess(workerId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/video")
    public ResponseEntity<String> getVideo(@RequestParam Long trackingNum) {
        String result = deliveryService.getVideo(trackingNum);
        return ResponseEntity.ok().body(result);
    }
}
