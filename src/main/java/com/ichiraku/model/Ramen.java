package com.ichiraku.model;

import com.ichiraku.enums.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Ramen extends Item {
    /*
    * *** Fields / Attributes ***
    * */
    private final Ingredient bowlSize;    // Ingredient representing the ramen portion size
    private final Ingredient baseBroth;   // Ingredient representing the broth type (e.g. Miso, Shoyu)
    private final Ingredient noodleType;  // Ingredient representing the noodle type
    private final Ingredient spiceLevel;  // Ingredient representing spice intensity
    private final List<Ingredient> customIngredients;   // Optional list of added or removed ingredients

    /*
     * *** Constructor ***
     * */
    private Ramen(Builder builder) {
        super(builder.id, builder.name, builder.description, ItemCategory.RAMEN);
        this.bowlSize = builder.bowlSize;
        this.baseBroth = builder.baseBroth;
        this.noodleType = builder.noodleType;
        this.spiceLevel = builder.spiceLevel;
        this.customIngredients = List.copyOf(builder.customIngredients);
    }

    /*
     * *** Getters / Setters ***
     * */
    public Ingredient getBowlSize() {
        return bowlSize;
    }

    public Ingredient getBaseBroth() {
        return baseBroth;
    }

    public Ingredient getNoodleType() {
        return noodleType;
    }

    public Ingredient getSpiceLevel() {
        return spiceLevel;
    }

    public List<Ingredient> getCustomIngredients() {
        return customIngredients;
    }

    /*
     * *** Methods ***
     * */

    /*
     * *** Overrides ***
     * */
    @Override
    public BigDecimal calculatePrice() {
        // Total price is sum of ingredient prices; base Ramen has no standalone price
        BigDecimal total = BigDecimal.ZERO;

        if (bowlSize != null) total = total.add(bowlSize.getBasePrice());
        if (baseBroth != null) total = total.add(baseBroth.getBasePrice());
        if (noodleType != null) total = total.add(noodleType.getBasePrice());
        if (spiceLevel != null) total = total.add(spiceLevel.getBasePrice());

        // Add cost of extra ingredients
        for (Ingredient ingredient : customIngredients) {
            total = total.add(ingredient.getBasePrice());
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return name + " [" +
                (baseBroth != null ? baseBroth.getName() : "No Broth") + " | " +
                (noodleType != null ? noodleType.getName() : "No Noodles") + " | " +
                (spiceLevel != null ? spiceLevel.getName() : "No Spice") + " | " +
                (bowlSize != null ? bowlSize.getName() : "No Size") +
                "] $" + calculatePrice();
    }

    /*
     * *** Nested Builder Class ***
     * */
    public static class Builder {
        // *** Builder Fields ***
        private final String id;
        private final String name;
        private final String description;
        private Ingredient bowlSize;
        private Ingredient baseBroth;
        private Ingredient noodleType;
        private Ingredient spiceLevel;
        private final List<Ingredient> customIngredients = new ArrayList<>();

        // *** Constructor ***
        public Builder(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        // *** Builder Methods (Chained) ***
        public Builder setBowlSize(Ingredient bowlSize) {
            this.bowlSize = bowlSize;
            return this;
        }

        public Builder setBroth(Ingredient broth) {
            this.baseBroth = broth;
            return this;
        }

        public Builder setNoodle(Ingredient noodleType) {
            this.noodleType = noodleType;
            return this;
        }

        public Builder addIngredient(Ingredient ingredient) {
            if (ingredient != null) {
                customIngredients.add(ingredient);
            }
            return this;
        }

        public Builder removeIngredient(Ingredient ingredient) {
            customIngredients.remove(ingredient);
            return this;
        }

        public Builder addCustomIngredients(List<Ingredient> ingredients) {
            if (ingredients != null) {
                customIngredients.addAll(new ArrayList<>(ingredients));
            }
            return this;
        }

        public Builder setSpice(Ingredient level) {
            this.spiceLevel = level;
            return this;
        }

        public Ramen build() {
            if (bowlSize == null || baseBroth == null || noodleType == null || spiceLevel == null) {
                throw new IllegalStateException("Missing required ingredients for ramen build");
            }
            return new Ramen(this);
        }
    }
}
