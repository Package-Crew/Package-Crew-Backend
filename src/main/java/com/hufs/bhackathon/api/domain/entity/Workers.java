package com.hufs.bhackathon.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Workers {

    @Id
    @GeneratedValue
    private Long id;

    private String memo;
    private String workerUrl;

    @Builder
    public Workers(String memo, String workerUrl, Work work) {
        this.memo = memo;
        this.workerUrl = workerUrl;
        this.work = work;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @OneToMany(mappedBy = "workers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveryList = new ArrayList<>();
}
