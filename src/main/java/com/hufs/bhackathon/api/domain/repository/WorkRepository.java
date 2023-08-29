package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Users;
import com.hufs.bhackathon.api.domain.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query("SELECT w FROM Work w WHERE w.users = :user AND w.done = 0")
    List<Work> findByUserYet(Users user);

    @Query("SELECT w FROM Work w WHERE w.users = :user AND w.done = 1")
    List<Work> findByUserDone(Users user);
}
