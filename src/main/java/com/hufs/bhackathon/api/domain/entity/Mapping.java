package com.hufs.bhackathon.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mapping {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder
    public Mapping(Item item, Delivery delivery){
        this.item = item;
        this.delivery = delivery;
    }

    public static Mapping of(Item item, Delivery delivery) {
        return Mapping.builder()
                .item(item)
                .delivery(delivery)
                .build();
    }
}
