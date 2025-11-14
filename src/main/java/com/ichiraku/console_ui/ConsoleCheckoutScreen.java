package com.ichiraku.console_ui;

import com.ichiraku.controller.ConsoleController;
import com.ichiraku.model.*;
import com.ichiraku.enums.PaymentType;
import com.ichiraku.persistence.ReceiptFileHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleCheckoutScreen {

    private final ConsoleController controller;

    public ConsoleCheckoutScreen(ConsoleController controller) {
        this.controller = controller;
    }

    public void display() {
        Scanner sc = controller.getScanner();
        Cart cart = controller.getCurrentCart();

        if (cart.getItems().isEmpty()) {
            System.out.println("\n🛒 Your cart is empty. Cannot checkout.");
            return;
        }

        System.out.println("\n=== 💳 Checkout ===");
        System.out.println("Your cart:");
        System.out.println(cart);

        // --- Step 1: Collect Customer Info ---
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter your contact number (optional, press enter to skip): ");
        String contact = sc.nextLine().trim();
        if (contact.isEmpty()) contact = null;

        System.out.print("Enter your email (optional, press enter to skip): ");
        String email = sc.nextLine().trim();
        if (email.isEmpty()) email = null;

        Customer customer = new Customer(UUID.randomUUID().toString(), name, contact, email);

        // --- Step 2: Select Payment Type ---
        PaymentType paymentMethod = promptForPaymentType(sc);

        // --- Step 3: Confirm payment amount ---
        BigDecimal totalAmount = cart.calculateSubtotal();
        System.out.println("Total amount to pay: $" + totalAmount);

        // --- Step 4: Optional notes ---
        System.out.print("Enter any notes (coupon code, special request) or press enter to skip: ");
        String notes = sc.nextLine().trim();
        if (notes.isEmpty()) notes = null;

        // --- Step 5: Create BillingInfo ---
        BillingInfo billingInfo = new BillingInfo(
                UUID.randomUUID().toString(),
                customer,
                paymentMethod,
                totalAmount,
                LocalDateTime.now(),
                notes
        );

        // --- Step 6: Create Transaction ---
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                cart.getItems(),
                billingInfo
        );

        // --- Step 7: Save Transaction ---
        // You can implement controller.getDataManager().saveTransaction(transaction) later
        System.out.println("\n✅ Checkout complete!");
        System.out.println("Transaction summary:");
        System.out.println(transaction);
        System.out.println("\nItems purchased:");
        System.out.println(transaction.getItemSummary());
        ReceiptFileHandler receiptHandler = new ReceiptFileHandler();
        receiptHandler.writeReceipt(transaction);

        // --- Step 8: Clear cart and return to main menu ---
        cart.clear();
        System.out.println("\nReturning to main menu...");
    }

    private PaymentType promptForPaymentType(Scanner sc) {
        PaymentType[] types = PaymentType.values();
        while (true) {
            System.out.println("\nSelect payment method:");
            for (int i = 0; i < types.length; i++) {
                System.out.printf("%d. %s\n", i + 1, types[i]);
            }
            System.out.print("Enter number: ");
            String input = sc.nextLine();

            try {
                int selection = Integer.parseInt(input);
                if (selection >= 1 && selection <= types.length) {
                    return types[selection - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid selection, try again.");
        }
    }
}
