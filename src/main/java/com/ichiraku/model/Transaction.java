package com.ichiraku.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import com.ichiraku.enums.*;

public class Transaction {
    // --- Fields / Attributes ---
    private final String transactionId;          // Unique identifier for this transaction
    private final Map<Item, Integer> cartItems;  // Items and their quantities from the cart
    private final BillingInfo billingInfo;       // Associated billing record
    private final LocalDateTime transactionDate; // Timestamp of checkout
    private final BigDecimal totalAmount;        // Cached total of all items in cart
    private TransactionStatus status;            // Enum (PENDING, COMPLETED, REFUNDED, CANCELLED)

    // --- Constructor ---
    public Transaction(String transactionId, Map<Item, Integer> cartItems, BillingInfo billingInfo) {
        this.transactionId = transactionId;
        this.cartItems = Map.copyOf(cartItems); // Immutable copy to prevent external modification
        this.billingInfo = billingInfo;
        this.transactionDate = LocalDateTime.now();
        this.totalAmount = calculateTotal();
        this.status = TransactionStatus.COMPLETED;
    }

    // --- Methods ---
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            if (item != null && quantity != null && quantity > 0) {
                total = total.add(item.calculatePrice().multiply(BigDecimal.valueOf(quantity)));
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public String getItemSummary() {
        StringBuilder summary = new StringBuilder();
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            summary.append(entry.getKey().getName())
                    .append(" x")
                    .append(entry.getValue())
                    .append("\n");
        }
        return summary.toString();
    }

    public void markRefunded() {
        this.status = TransactionStatus.REFUNDED;
    }

    // --- Getters ---
    public String getTransactionId() {
        return transactionId;
    }

    public Map<Item, Integer> getCartItems() {
        return cartItems;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Transaction [" + transactionId + "]\n"
                + billingInfo.getCustomer().getName() + " | "
                + billingInfo.getPaymentSummary() + "\n"
                + "Total: $" + totalAmount;
    }

}

