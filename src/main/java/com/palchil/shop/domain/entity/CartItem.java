package com.palchil.shop.domain.entity;

import com.palchil.shop.repository.OrderRepository;
import com.palchil.shop.service.OrderService;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cart_item SET deleted = true where cart_item_id = ?")
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id;

    //item
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //수량
    private int count;

    //판매가격
    private int orderPrice;

    private boolean deleted = Boolean.FALSE;

    //==생성 메서드==//
    public static CartItem createCartItem(Item item, Order order, int orderPrice, int count) {
        return CartItem.builder()
                .item(item)
                .order(order)
                .orderPrice(orderPrice)
                .count(count)
                .build();
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
