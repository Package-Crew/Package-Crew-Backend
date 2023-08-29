package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Delivery;
import com.hufs.bhackathon.api.domain.entity.Work;
import com.hufs.bhackathon.api.domain.entity.Workers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByTrackingNum(Long tackingNum);

    List<Delivery> findByWork(Work work);

    @Query("SELECT d FROM Delivery d WHERE d.work = :work AND d.done = 1")
    List<Delivery> findByWorkDone(Work work);

    @Query("SELECT d FROM Delivery d WHERE d.work = :work AND d.done = 1 ORDER BY d.packagingDate DESC")
    List<Delivery> findByWorkPageable(Work work, Pageable pageable);

    List<Delivery> findByWorkers(Workers worker);

    @Query("SELECT d FROM Delivery d WHERE d.workers = :worker AND d.done = 1")
    List<Delivery> findByWorkersDone(Workers worker);
}
