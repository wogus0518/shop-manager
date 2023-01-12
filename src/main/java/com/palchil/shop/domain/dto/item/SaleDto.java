package com.palchil.shop.domain.dto.item;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class SaleDto {
    private String saleSort;
    private String saleAmount;
    private String saleQuantity;
}
