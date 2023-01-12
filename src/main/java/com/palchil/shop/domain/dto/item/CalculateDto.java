package com.palchil.shop.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CalculateDto {
    private String totalPrice;
    private int received;
}
