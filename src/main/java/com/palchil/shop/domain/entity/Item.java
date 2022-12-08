package com.palchil.shop.domain.entity;

import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    private Long id;
    private String purchaseDate;
    private String store;
    private String buyName;
    private String saleName;
    private String color;

    @Column(length = 10000)
    private String base64;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer quantity;
    private Integer unitCost;
    private Integer price;

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
