package com.ichiraku.model;

import com.ichiraku.enums.ItemCategory;

import java.math.BigDecimal;

/**
 * Super Abstract Immutable Item class.
 * <p>
 * Parent class for all purchasable products;
 * defines shared fields like id, name, price, and category.
 */
public abstract class Item {
    /*
    * *** Fields / Attributes ***
    * */
    protected final String id;                    // Unique identifier for the item
    protected final String name;                  // Name of the item (e.g., "Shoyu Ramen")
    protected final String description;           // Short description of the item
    protected final BigDecimal basePrice;         // Base price of the item
    protected final ItemCategory itemCategory;    // Enum defining the category.

    /*
    * *** Constructor(s) ***
    * */
    public Item(String id, String name, String description, BigDecimal basePrice, ItemCategory itemCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.itemCategory = itemCategory;
    }

    public Item(String id, String name, String description, ItemCategory category) {
        this(id, name, description, BigDecimal.ZERO, category);
    }

    /*
    * *** Abstract Methods ***
    * */
    public abstract BigDecimal calculatePrice();

    /*
    * *** Concrete Methods ***
    * */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getTotalPrice() {
        return calculatePrice();
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    /*
    * *** Overrides ***
    * */
    @Override
    public String toString() {
        return name + " [" + itemCategory + " | $" + basePrice + "]";
    }
}
