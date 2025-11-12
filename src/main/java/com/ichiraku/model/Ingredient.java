package com.ichiraku.model;

import com.ichiraku.enums.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ingredient extends Item{
    /*
    * *** Fields / Attributes ***
    * */
    private final IngredientCategory ingredientCategory;

    /*
    * *** Constructor ***
    * */
    public Ingredient(String id, String name, String description, BigDecimal basePrice, IngredientCategory ingredientCategory) {
        super(id, name, description, basePrice, ItemCategory.INGREDIENT);
        this.ingredientCategory = ingredientCategory;
    }

    /*
     * *** Getters ***
     * */
    public IngredientCategory getIngredientCategory() {
        return ingredientCategory;
    }

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
        return name + " [" + ingredientCategory + " | $" + basePrice + "]";
    }
}
