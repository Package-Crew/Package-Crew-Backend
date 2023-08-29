package com.hufs.bhackathon.api.domain.entity;

import com.hufs.bhackathon.api.dto.request.WorkRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Work {

    @Id
    @GeneratedValue
    private Long id;

    private String workName;

    private Integer done; // 0: 아직 안됨, 1: 완료
    private Date startDate;
    private Date endDate;

    @Builder
    public Work(String workName, Integer done, Date startDate, Date endDate, Users users) {
        this.workName = workName;
        this.done = done;
        this.startDate = startDate;
        this.endDate = endDate;
        this.users = users;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workers> workersList = new ArrayList<>();

    public static Work of(WorkRequestDto workRequestDto, Users user) {
        return Work.builder()
                .workName(workRequestDto.getWorkName())
                .done(Integer.valueOf(0))
                .startDate(workRequestDto.getStartDate())
                .endDate(workRequestDto.getEndDate())
                .users(user)
                .build();
    }
}
