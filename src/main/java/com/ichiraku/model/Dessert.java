package com.ichiraku.model;

import com.ichiraku.enums.ItemCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Dessert extends Item{
    /*
    * *** Fields / Attributes ***
    * */

    /*
     * *** Constructor ***
     * */
    public Dessert(String id, String name, String description, BigDecimal basePrice) {
        super(id, name, description, basePrice, ItemCategory.DESSERT);
    }

    /*
     * *** Getters/Setters ***
     * */

    /*
     * *** Methods ***
     * */

    /*
     * *** Overrides ***
     * */
    @Override
    public BigDecimal calculatePrice() {
        return basePrice.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return name + " [$" + calculatePrice() + "]";
    }
}
