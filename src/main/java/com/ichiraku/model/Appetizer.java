package com.ichiraku.model;

import com.ichiraku.enums.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Appetizer extends Item{
    /*
    * *** Fields / Attributes ***
    * */

    /*
    * *** Constructor ***
    * */
    public Appetizer(String id, String name, String description, BigDecimal basePrice) {
        super(id, name, description, basePrice, ItemCategory.APPETIZER);
    }

    /*
    * *** Getters/Setters ***
    * */

    /*
    * *** Methods ***
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