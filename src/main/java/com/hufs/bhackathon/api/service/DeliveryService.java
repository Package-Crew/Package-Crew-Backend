package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.Delivery;
import com.hufs.bhackathon.api.domain.entity.Users;
import com.hufs.bhackathon.api.domain.entity.Work;
import com.hufs.bhackathon.api.domain.repository.UsersRepository;
import com.hufs.bhackathon.api.domain.repository.DeliveryRepository;
import com.hufs.bhackathon.api.domain.repository.WorkRepository;
import com.hufs.bhackathon.api.dto.request.DeliveryRequestDto;
import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final WorkRepository workRepository;
    private final UsersRepository usersRepository;
    private final MappingService mappingService;
    private final ItemService itemService;

    @Transactional
    public Long postWork(Long userId, WorkRequestDto workRequestDto) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Work work = workRepository.save(Work.of(workRequestDto, user));
        return work.getId();
    }

    @Transactional
    public String postDelivery(Long userId, Long workId, List<DeliveryRequestDto> deliveryRequestDto) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Work work = workRepository.findById(workId).orElseThrow(() -> new CustomException(ErrorCode.WORK_NOT_FOUND));

        for (DeliveryRequestDto delivery : deliveryRequestDto) {
            deliveryRepository.save(Delivery.of(delivery.getTrackingNum(), user)); // 운송장 등록
            mappingService.mapToDeliveryAndItem(delivery, delivery.getItems()); // 운송장 번호랑 이미 등록했던 상품이랑 매핑하기
        }
        return "배송이 등록되었습니다.";
    }
}
