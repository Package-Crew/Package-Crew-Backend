package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Delivery;
import com.hufs.bhackathon.api.domain.entity.Item;
import com.hufs.bhackathon.api.domain.entity.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MappingRepository extends JpaRepository<Mapping, Long> {
    @Query("SELECT m.item FROM Mapping m WHERE m.delivery = :delivery")
    List<Item> findByDelivery(@Param("delivery") Delivery delivery);
}
