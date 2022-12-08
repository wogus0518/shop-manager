package com.palchil.shop.domain.enumerate;

public enum Category {
    OUTER("아우터"), DRESS("원피스"), SWEATER("스웨터"), SHIRT("셔츠"),
    PANT("바지"), SKIRT("치마"), SHOE("신발"), ACC("악세사리");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
