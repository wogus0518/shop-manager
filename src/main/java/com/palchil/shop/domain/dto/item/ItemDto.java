package com.palchil.shop.domain.dto.item;

import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String base64;
    private String purchaseDate;
    private String store;
    private String buyName;
    private String saleName;
    private String color;
    private String category;
    private String size;
    private String gender;
    private String quantity;
    private String unitCost;
    private String price;
    private String stock;

    public Item toEntity() {
        return Item.builder()
                .id(id)
                .purchaseDate(purchaseDate)
                .store(store)
                .buyName(buyName)
                .saleName(saleName)
                .color(color)
                .category(Category.valueOf(category))
                .size(Size.valueOf(size))
                .gender(Gender.valueOf(gender))
                .quantity(Integer.parseInt(quantity))
                .unitCost(Integer.parseInt(unitCost))
                .price(Integer.parseInt(price))
                .base64(base64)
                .stock(Integer.parseInt(stock))
                .build();
    }
}
