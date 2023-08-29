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
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String itemName;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mapping> mappingList = new ArrayList<>();

    @Builder
    public Item(String itemName, String imageUrl, Users users){
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.users = users;
    }

    public static Item of(String itemName, String imageUrl, Users users){
        return Item.builder()
                .itemName(itemName)
                .imageUrl(imageUrl)
                .users(users)
                .build();
    }
}
