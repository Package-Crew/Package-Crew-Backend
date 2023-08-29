package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Item;
import com.hufs.bhackathon.api.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.users = :user")
    List<Item> findByUser(Users user);
}
