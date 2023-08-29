package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.Delivery;
import com.hufs.bhackathon.api.domain.entity.Item;
import com.hufs.bhackathon.api.domain.entity.Users;
import com.hufs.bhackathon.api.domain.entity.Work;
import com.hufs.bhackathon.api.domain.repository.MappingRepository;
import com.hufs.bhackathon.api.domain.repository.UsersRepository;
import com.hufs.bhackathon.api.domain.repository.DeliveryRepository;
import com.hufs.bhackathon.api.domain.repository.WorkRepository;
import com.hufs.bhackathon.api.dto.request.DeliveryRequestDto;
import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import com.hufs.bhackathon.api.dto.response.WorkResponseDto;
import com.hufs.bhackathon.api.dto.response.QrResponseDto;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService<nowWorkList> {

    private final DeliveryRepository deliveryRepository;
    private final WorkRepository workRepository;
    private final UsersRepository usersRepository;
    private final MappingService mappingService;
    private final MappingRepository mappingRepository;
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
            deliveryRepository.save(Delivery.of(delivery.getTrackingNum(), user, work)); // 운송장 등록
            mappingService.mapToDeliveryAndItem(delivery, delivery.getItems()); // 운송장 번호랑 이미 등록했던 상품이랑 매핑하기
        }
        return "배송이 등록되었습니다.";
    }

    @Transactional
    public QrResponseDto qrScan(Long trackingNum) {
        Delivery delivery = deliveryRepository.findByTrackingNum(trackingNum).orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
        List<Item> itemList = mappingRepository.findByDelivery(delivery);

        return QrResponseDto.of(trackingNum, itemList);
    }

    public List<WorkResponseDto> nowWork(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<WorkResponseDto> nowWorkResponseList = new ArrayList<>();

        List<Work> workList = workRepository.findByUserYet(user);
        for(Work work : workList) {
            int allDeliveryCount = deliveryRepository.findByWork(work).size();
        	int doneDeliveryCount = deliveryRepository.findByWorkDone(work).size();
            int avgCount = (int) ((double) doneDeliveryCount / (double) allDeliveryCount * 100.0);
            nowWorkResponseList.add(WorkResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), allDeliveryCount, doneDeliveryCount, avgCount ));
        }
        return nowWorkResponseList;
    }

    public List<WorkResponseDto> previousWork(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<WorkResponseDto> previousWorkResponseList = new ArrayList<>();

        List<Work> workList = workRepository.findByUserDone(user);
        for(Work work : workList) {
            int allDeliveryCount = deliveryRepository.findByWork(work).size();
        	int doneDeliveryCount = deliveryRepository.findByWorkDone(work).size();
            int avgCount = (int) ((double) doneDeliveryCount / (double) allDeliveryCount * 100.0);
            previousWorkResponseList.add(WorkResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), allDeliveryCount, doneDeliveryCount, avgCount ));
        }
        return previousWorkResponseList;
    }

//    public String packaging(Long trackingNum, Long workerId) {
//        Delivery delivery = deliveryRepository.findByTrackingNum(trackingNum).orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
//        Users worker = usersRepository.findById(workerId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
//        List<Item> itemList = mappingRepository.findByDelivery(delivery);
//
//        for (Item item : itemList) {
//            itemService.packaging(item.getId(), worker);
//        }
//        return "포장이 완료되었습니다.";
//    }
}
