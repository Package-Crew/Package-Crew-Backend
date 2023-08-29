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
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    private Long trackingNum;
    private String imageUrl;
    private Integer done;

    @Builder
    public Delivery(Long trackingNum, String imageUrl, Integer done, Workers workers, Users users) {
        this.trackingNum = trackingNum;
        this.imageUrl = imageUrl;
        this.done = done;
        this.workers = workers;
        this.users = users;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workers_id")
    private Workers workers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mapping> mappingList = new ArrayList<>();

    public static Delivery of(Long trackingNum, Users user) {
        return Delivery.builder()
                .trackingNum(trackingNum)
                .users(user)
                .done(0)
                .build();
    }
}
