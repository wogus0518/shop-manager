package com.palchil.shop.domain.dto.item;

import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.enumerate.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddItemDto {
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

    public Item toEntity() {
        return Item.builder()
                .purchaseDate(this.purchaseDate)
                .store(this.store)
                .buyName(this.buyName)
                .saleName(this.saleName)
                .color(this.color)
                .category(Category.valueOf(this.category))
                .size(Size.valueOf(this.size))
                .gender(Gender.valueOf(this.gender))
                .quantity(Integer.parseInt(this.quantity))
                .unitCost(Integer.parseInt(this.unitCost))
                .price(Integer.parseInt(this.price))
                .base64(null)
                .build();
    }
}
