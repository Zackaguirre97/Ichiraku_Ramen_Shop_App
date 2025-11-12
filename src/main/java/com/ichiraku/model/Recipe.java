package com.ichiraku.model;

import com.ichiraku.enums.ItemCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    /*
     * *** Fields / Attributes ***
     * */
    private final String id;                    // Unique identifier for the item
    private final String name;                  // Name of the item (e.g., "Shoyu Ramen")
    private final String description;           // Short description of the item
    private final ItemCategory itemCategory;    // Enum defining the category.
    private final List<Item> ingredients;       // List of Item-derived Ingredient objects that make up the recipe/core item.

    /*
     * *** Constructor ***
     * */
    public Recipe(String id, String name, String description, ItemCategory itemCategory, List<Item> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemCategory = itemCategory;
        this.ingredients = List.copyOf(ingredients);
    }

    /*
     * *** Getters/Setters ***
     * */

    /*
     * *** Methods ***
     * */
    public Item buildRecipe() {
        if (Objects.requireNonNull(itemCategory) == ItemCategory.RAMEN) {
            Ingredient bowlSize = null;
            Ingredient broth = null;
            Ingredient noodle = null;
            Ingredient spice = null;
            List<Ingredient> extras = new ArrayList<>();

            for (Item i : ingredients) {
                if (i instanceof Ingredient ing) {
                    switch (ing.getIngredientCategory()) {
                        case BOWL_SIZE -> bowlSize = ing;
                        case BROTH -> broth = ing;
                        case NOODLE -> noodle = ing;
                        case SPICE -> spice = ing;
                        default -> extras.add(ing);
                    }
                }
            }

            if (bowlSize == null || broth == null || noodle == null || spice == null) {
                throw new IllegalStateException("Incomplete recipe: missing one or more core ingredients");
            }

            return new Ramen.Builder(id, name, description)
                    .setBowlSize(bowlSize)
                    .setBroth(broth)
                    .setNoodle(noodle)
                    .setSpice(spice)
                    .addCustomIngredients(extras)
                    .build();
        }
        throw new IllegalStateException("Unknown item category");
    }

    /*
     * *** Overrides ***
     * */
}
