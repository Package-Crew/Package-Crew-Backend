package com.hufs.bhackathon.api.domain.entity;

import com.hufs.bhackathon.api.dto.request.JoinDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @Builder
    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> workList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveryList = new ArrayList<>();

    public static Users of(JoinDto joinDto) {
        return Users.builder()
                .email(joinDto.getEmail())
                .password(joinDto.getPassword())
                .build();
    }

}
