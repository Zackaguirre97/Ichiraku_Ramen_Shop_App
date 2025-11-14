package com.ichiraku.persistence;

import com.ichiraku.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ReceiptFileHandler {

    // Folder inside resources
    private static final String RECEIPT_FOLDER = "src/main/resources/receipts/";

    public ReceiptFileHandler() {
        ensureReceiptFolderExists();
    }

    private void ensureReceiptFolderExists() {
        try {
            Files.createDirectories(Path.of(RECEIPT_FOLDER));
        } catch (IOException e) {
            System.out.println("Error: IO exception encountered!");
        }
    }

    /**
     * Generates a receipt file for a completed transaction.
     */
    public void writeReceipt(Transaction transaction) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

        String fileName = RECEIPT_FOLDER + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(buildReceiptContent(transaction));

        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }

    /**
     * Builds the full receipt content text.
     */
    private String buildReceiptContent(Transaction tx) {
        StringBuilder sb = new StringBuilder();

        sb.append("====== Ichiraku Ramen Shop ======\n");
        sb.append("Receipt ID: ").append(tx.getTransactionId()).append("\n");
        // format date nicely
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sb.append("Date: ").append(tx.getTransactionDate().format(dtf)).append("\n\n");

        BillingInfo bi = tx.getBillingInfo();
        if (bi != null) {
            Customer c = bi.getCustomer();
            if (c != null) {
                sb.append("Customer: ").append(c.getName()).append("\n");
                if (c.getContactNumber() != null) sb.append("Phone: ").append(c.getContactNumber()).append("\n");
                if (c.getEmail() != null) sb.append("Email: ").append(c.getEmail()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("--------- Order Summary ---------\n");
        for (Map.Entry<Item, Integer> entry : tx.getCartItems().entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            var price = item.calculatePrice().multiply(java.math.BigDecimal.valueOf(qty))
                    .setScale(2, RoundingMode.HALF_UP);

            sb.append(String.format("%d × %s  -  $%s\n", qty, item.getName(), price));

            if (item instanceof Ramen ramen) {
                sb.append("   Ingredients:\n");
                sb.append("      • Bowl Size: ").append(ramen.getBowlSize().getName()).append("\n");
                sb.append("      • Broth: ").append(ramen.getBaseBroth().getName()).append("\n");
                sb.append("      • Noodles: ").append(ramen.getNoodleType().getName()).append("\n");
                sb.append("      • Spice Level: ").append(ramen.getSpiceLevel().getName()).append("\n");

                if (!ramen.getCustomIngredients().isEmpty()) {
                    sb.append("      • Extras:\n");
                    for (Ingredient ing : ramen.getCustomIngredients()) {
                        sb.append("          - ").append(ing.getName()).append("\n");
                    }
                }
            }

            sb.append("\n");
        }

        sb.append("---------------------------------\n");
        sb.append(String.format("Total Paid: $%s\n", tx.getTotalAmount()));

        if (bi != null) {
            sb.append("Payment Method: ").append(bi.getPaymentSummary()).append("\n");
            if (bi.getNotes() != null && !bi.getNotes().isBlank()) {
                sb.append("Notes: ").append(bi.getNotes()).append("\n");
            }
        }

        sb.append("\n===== Thank you for dining! =====\n");

        return sb.toString();
    }

}

