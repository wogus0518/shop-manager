package com.palchil.shop.domain.entity;

import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import com.palchil.shop.exception.NotEnoughStockException;
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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Integer stock;
    private Integer unitCost;
    private Integer price;

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    //==비즈니스 로직==//
    public void addStock(int q) {
        this.stock += q;
    }

    public void removeStock(int q) {
        int restStock = this.stock - q;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stock = restStock;
    }

    public void addQuantity(int q) {
        this.quantity += q;
    }

    public void removeQuantity(int q) {
        int restQuantity = this.quantity - q;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.quantity = restQuantity;
    }
}
