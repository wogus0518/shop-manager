package com.palchil.shop.domain.enumerate;

public enum Gender {
    MAN("남자"), WOMAN("여자"), BOTH("공용");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
