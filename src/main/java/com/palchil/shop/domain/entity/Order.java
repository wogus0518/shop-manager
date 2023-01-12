package com.palchil.shop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palchil.shop.domain.dto.item.SaleDto;
import com.palchil.shop.domain.enumerate.OrderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<CartItem> cartItems = new ArrayList<>();

    //판매 시각
    private LocalDateTime orderDate; //주문시간

    private int totalPrice; //판매할 때 금액
    private int totalCount; //판매 수량

    @Enumerated(EnumType.STRING)
    private OrderType orderType;


    public static Order createOrder() {
        Order order = new Order();
        order.setOrderType(OrderType.PROCESSING);
        return order;
    }

    public void closed() {
        this.orderDate = LocalDateTime.now();
        this.orderType = OrderType.CLOSED;
    }
}
