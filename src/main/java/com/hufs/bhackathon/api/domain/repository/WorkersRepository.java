package com.hufs.bhackathon.api.domain.repository;

import com.hufs.bhackathon.api.domain.entity.Work;
import com.hufs.bhackathon.api.domain.entity.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WorkersRepository extends JpaRepository<Workers, Long> {
    List<Workers> findByWork(Work work);
}
