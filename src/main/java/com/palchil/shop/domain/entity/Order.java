package com.palchil.shop.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //상품 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //판매 시각
    private LocalDateTime orderDate; //주문시간

    public static Order createOrder(Item item) {
        return Order.builder()
                .item(item)
                .orderDate(LocalDateTime.now())
                .build();
    }

}
