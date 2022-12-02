package com.palchil.shop.web.dto.item;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddItemDto {
    @NotBlank
    private String category;
    @NotBlank
    private String name;
    @NotBlank
    private String store;
    @NotNull
    private int cost;
    @NotNull
    private int price;
    @NotNull
    private int medium;
    @NotNull
    private int large;
    @NotNull
    private int free;
    @NotNull
    private int shoes230;
    @NotNull
    private int shoes240;
    @NotNull
    private int shoes250;
}
