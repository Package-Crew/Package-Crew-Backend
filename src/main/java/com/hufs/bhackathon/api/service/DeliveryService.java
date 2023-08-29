package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.*;
import com.hufs.bhackathon.api.domain.repository.*;
import com.hufs.bhackathon.api.dto.request.DeliveryRequestDto;
import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import com.hufs.bhackathon.api.dto.response.*;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final WorkRepository workRepository;
    private final UsersRepository usersRepository;
    private final MappingService mappingService;
    private final MappingRepository mappingRepository;
    private final WorkersRepository workersRepository;
    private final S3Service s3Service;

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

    @Transactional(readOnly = true)
    public QrResponseDto qrScan(Long trackingNum) {
        Delivery delivery = deliveryRepository.findByTrackingNum(trackingNum).orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
        List<Item> itemList = mappingRepository.findByDelivery(delivery);

        return QrResponseDto.of(trackingNum, itemList);
    }

    @Transactional(readOnly = true)
    public List<WorkResponseDto> nowWork(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<WorkResponseDto> nowWorkResponseList = new ArrayList<>();

        List<Work> workList = workRepository.findByUserYet(user);
        for(Work work : workList) {
            int allDeliveryCount = deliveryRepository.findByWork(work).size();
        	int doneDeliveryCount = deliveryRepository.findByWorkDone(work).size();
            int avgCount = (int) ((double) doneDeliveryCount / (double) allDeliveryCount * 100.0);
            int workers = workersRepository.findByWork(work).size();
            nowWorkResponseList.add(WorkResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), workers, allDeliveryCount, doneDeliveryCount, avgCount ));
        }
        return nowWorkResponseList;
    }

    @Transactional(readOnly = true)
    public List<WorkResponseDto> previousWork(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<WorkResponseDto> previousWorkResponseList = new ArrayList<>();

        List<Work> workList = workRepository.findByUserDone(user);
        for(Work work : workList) {
            int allDeliveryCount = deliveryRepository.findByWork(work).size();
        	int doneDeliveryCount = deliveryRepository.findByWorkDone(work).size();
            int avgCount = (int) ((double) doneDeliveryCount / (double) allDeliveryCount * 100.0);
            int workers = workersRepository.findByWork(work).size();
            previousWorkResponseList.add(WorkResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), workers, allDeliveryCount, doneDeliveryCount, avgCount ));
        }
        return previousWorkResponseList;
    }

    @Transactional
    public String packaging(Long trackingNum, Long workerId, MultipartFile video) {
        Delivery delivery = deliveryRepository.findByTrackingNum(trackingNum).orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
        String videoUrl = s3Service.uploadImage(video);
        Workers worker = workersRepository.findById(workerId).orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
        // delivery에서 작업한 worker 등록하기
        delivery.packaging(worker, videoUrl);
        deliveryRepository.save(delivery);
        return "포장이 완료되었습니다.";
    }

    @Transactional(readOnly = true)
    public DashBoardResponseDto dashboard(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new CustomException(ErrorCode.WORK_NOT_FOUND));
        List<Delivery> deliveryList = deliveryRepository.findByWorkPageable(work, PageRequest.of(0,7));
        List<DashBoardDeliveryResponseDto> dashBoardDeliveryResponseDtoList = new ArrayList<>();
        int totalDeliveryCount = deliveryRepository.findByWork(work).size();
        int doneDeliveryCount = deliveryRepository.findByWorkDone(work).size();
        int avgCount = (int) ((double) doneDeliveryCount / (double) totalDeliveryCount * 100.0);
        // 남은 시간 구하기 , Date로 된거 LocalDateTime으로 바꾸기
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = work.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        long limit = DAYS.between(now, endDate);

        // 최신 배송 정보 7개 넣기
        for(Delivery delivery : deliveryList) {
            List<Item> itemList = mappingRepository.findByDelivery(delivery);
            dashBoardDeliveryResponseDtoList.add(DashBoardDeliveryResponseDto.of(delivery.getTrackingNum(),delivery.getDone(), itemList));
        }
        return DashBoardResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), totalDeliveryCount, doneDeliveryCount, avgCount, limit, dashBoardDeliveryResponseDtoList);
    }


    @Transactional(readOnly = true)
    public GetDeliveryResponseDto getDelivery(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new CustomException(ErrorCode.WORK_NOT_FOUND));
        List<Delivery> deliveryList = deliveryRepository.findByWork(work);
        List<DeliveryResponseDto> getDeliveryResponseDtoList = new ArrayList<>();

        for(Delivery delivery : deliveryList) {
            List<Item> itemList = mappingRepository.findByDelivery(delivery);
            Long workerId = 0L;
            try{
                workerId = delivery.getWorkers().getId();
            } catch (NullPointerException e) {
            }
            getDeliveryResponseDtoList.add(DeliveryResponseDto.of(delivery.getTrackingNum(), delivery.getDone(), workerId, itemList));
        }
        return GetDeliveryResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), getDeliveryResponseDtoList);
    }

    @Transactional(readOnly = true)
    public List<DashBoardDeliveryResponseDto> getDeliveryAll(Long workerId) {
        Workers worker = workersRepository.findById(workerId).orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
        List<Delivery> deliveryList = deliveryRepository.findByWorkers(worker);
        List<DashBoardDeliveryResponseDto> dashBoardDeliveryResponseDtoList = new ArrayList<>();

        for(Delivery delivery : deliveryList) {
            List<Item> itemList = mappingRepository.findByDelivery(delivery);
            dashBoardDeliveryResponseDtoList.add(DashBoardDeliveryResponseDto.of(delivery.getTrackingNum(), delivery.getDone(), itemList));
        }
        return dashBoardDeliveryResponseDtoList;
    }

    @Transactional(readOnly = true)
    public ManageWorkerResponseDto getWorkerAll(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new CustomException(ErrorCode.WORK_NOT_FOUND));
        List<Workers> workersList = workersRepository.findByWork(work);
        List<AllWorkerResponseDto> allWorkerResponseDtos = new ArrayList<>();

        for(Workers worker : workersList) {
            int allDeliveryCount = deliveryRepository.findByWorkers(worker).size();
            allWorkerResponseDtos.add(AllWorkerResponseDto.of(worker.getId(), worker.getMemo(), allDeliveryCount));
        }
        return ManageWorkerResponseDto.of(work.getWorkName(), work.getStartDate(), work.getEndDate(), allWorkerResponseDtos);
    }

    @Transactional(readOnly = true)
    public GetDeliveryResponseDto getProcess(Long workerId) {
        Workers worker = workersRepository.findById(workerId).orElseThrow(() -> new CustomException(ErrorCode.WORKER_NOT_FOUND));
        List<Delivery> deliveryList = deliveryRepository.findByWorkers(worker);
        List<DeliveryResponseDto> getDeliveryResponseDtoList = new ArrayList<>();

        for(Delivery delivery : deliveryList) {
            List<Item> itemList = mappingRepository.findByDelivery(delivery);
            getDeliveryResponseDtoList.add(DeliveryResponseDto.of(delivery.getTrackingNum(), delivery.getDone(), workerId, itemList));
        }
        return GetDeliveryResponseDto.of(worker.getWork().getWorkName(), worker.getWork().getStartDate(), worker.getWork().getEndDate(), getDeliveryResponseDtoList);
    }
}
