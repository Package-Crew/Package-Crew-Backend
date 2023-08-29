package com.hufs.bhackathon.api.domain.entity;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String videoUrl;
    private Integer done;
    private LocalDateTime packagingDate;

    @Builder
    public Delivery(Long trackingNum, String videoUrl, Integer done, LocalDateTime packagingDate, Workers workers, Work work, Users users) {
        this.trackingNum = trackingNum;
        this.videoUrl = videoUrl;
        this.done = done;
        this.packagingDate = packagingDate;
        this.workers = workers;
        this.users = users;
        this.work = work;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workers_id")
    private Workers workers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mapping> mappingList = new ArrayList<>();

    public static Delivery of(Long trackingNum, Users user, Work work) {
        return Delivery.builder()
                .trackingNum(trackingNum)
                .users(user)
                .work(work)
                .done(0)
                .build();
    }

    public void packaging(Workers workers, String videoUrl) {
        this.workers = workers;
        this.done = 1; // 완료로 변경
        this.videoUrl = videoUrl;
        this.packagingDate = LocalDateTime.now();
    }
}
