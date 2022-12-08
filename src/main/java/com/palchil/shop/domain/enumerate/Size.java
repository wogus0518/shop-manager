package com.palchil.shop.domain.enumerate;

public enum Size {
    F("Free"), S("Small"), M("Medium"), L("Large"),
    SHOE_225("225"), SHOE_230("230"), SHOE_235("235"), SHOE_240("240");

    private final String description;

    Size(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
