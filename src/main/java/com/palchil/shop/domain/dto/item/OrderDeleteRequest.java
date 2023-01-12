package com.palchil.shop.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderDeleteRequest {
    private final String orderId;
}
