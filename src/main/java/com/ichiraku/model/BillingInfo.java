package com.ichiraku.model;

import com.ichiraku.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillingInfo {
    // --- Fields / Attributes ---
    private final String billingId;                 // Unique identifier for this billing record
    private final Customer customer;                // The paying customer
    private final PaymentType paymentMethod;        // Enum (CASH, CARD, ONLINE, etc.)
    private final BigDecimal amountPaid;            // Total paid amount
    private final LocalDateTime transactionDate;    // Timestamp of payment
    private final String notes;                     // Optional notes (e.g., "Used coupon")

    // --- Constructor ---
    public BillingInfo(String billingId, Customer customer, PaymentType paymentMethod,
                       BigDecimal amountPaid, LocalDateTime transactionDate, String notes) {
        this.billingId = billingId;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.transactionDate = transactionDate;
        this.notes = notes;
    }

    // --- Methods ---

    public String getBillingId() {
        return billingId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public PaymentType getPaymentMethod() {
        return paymentMethod;
    }
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public String getNotes() {
        return notes;
    }

    public String getPaymentSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = transactionDate.format(formatter);

        return "Paid $" + amountPaid
                + " via " + paymentMethod
                + " on " + formattedDate;
    }

    @Override
    public String toString() {
        return "Billing[" + billingId + "] "
                + (customer != null ? customer.getName() : "Unknown Customer")
                + " | " + getPaymentSummary();
    }
}

