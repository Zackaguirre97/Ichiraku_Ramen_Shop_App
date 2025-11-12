package com.ichiraku.model;

import com.ichiraku.enums.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Drink extends Item{
    /*
    * *** Fields / Attributes ***
    * */

    /*
     * *** Constructor ***
     * */
    public Drink(String id, String name, String description, BigDecimal price) {
        super(id, name, description, price, ItemCategory.DRINK);
    }

    /*
     * *** Methods / Overrides ***
     * */

    /*
     * *** Methods / Overrides ***
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
