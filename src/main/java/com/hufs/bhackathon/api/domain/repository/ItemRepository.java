package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
