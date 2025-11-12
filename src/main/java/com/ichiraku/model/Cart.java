package com.ichiraku.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    // --- Fields / Attributes ---
    private final Map<Item, Integer> cartItems;        // Tracks item objects and their quantities

    // --- Constructor ---
    public Cart(){
        cartItems = new HashMap<>();
    }

    // --- Methods ---
    public void addItem(Item item, Integer quantity) {
        if (item == null || quantity == null || quantity <= 0) return;
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(Item item) {
        cartItems.remove(item);
    }

    public void updateQuantity(Item item, int newQuantity) {
        if (newQuantity <= 0) {
            cartItems.remove(item); // remove if quantity is zero or less
        } else if (cartItems.containsKey(item)) {
            cartItems.put(item, newQuantity); // put() is fine — replaces old value
        }
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();

            if (item != null && quantity != null && quantity > 0) {
                BigDecimal itemTotal = item.calculatePrice()
                        .multiply(BigDecimal.valueOf(quantity));
                subtotal = subtotal.add(itemTotal);
            }
        }

        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    public void clear() {
        cartItems.clear();
    }

    public Map<Item, Integer> getItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        if (cartItems.isEmpty()) {
            return "Cart is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Cart Contents:\n");

        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();

            if (item != null && quantity != null && quantity > 0) {
                BigDecimal itemTotal = item.calculatePrice()
                        .multiply(BigDecimal.valueOf(quantity))
                        .setScale(2, RoundingMode.HALF_UP);
                sb.append(quantity)
                        .append("× ")
                        .append(item.getName())
                        .append(" - $")
                        .append(itemTotal)
                        .append("\n");
            }
        }

        sb.append("Subtotal: $").append(calculateSubtotal());
        return sb.toString();
    }

}
