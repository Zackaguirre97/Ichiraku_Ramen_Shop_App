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

        // Temporary stored info during checkout
        String name = null;
        String contact = null;
        String email = null;
        PaymentType paymentMethod = null;
        String notes = null;

        boolean inCheckout = true;
        while (inCheckout) {

            System.out.println("\n=== 💳 Checkout ===");
            System.out.println("Your cart:");
            System.out.println(cart);

            System.out.println("\n--- Current Checkout Info ---");
            System.out.println("Name: " + (name != null ? name : "[Not entered]"));
            System.out.println("Contact: " + (contact != null ? contact : "[Optional]"));
            System.out.println("Email: " + (email != null ? email : "[Optional]"));
            System.out.println("Payment: " + (paymentMethod != null ? paymentMethod : "[Not selected]"));
            System.out.println("Notes: " + (notes != null && !notes.isBlank() ? notes : "[None]"));

            System.out.println("\nOptions:");
            System.out.println("1. Enter / Edit Customer Info");
            System.out.println("2. Select Payment Method");
            System.out.println("3. Add Notes");
            System.out.println("4. Confirm & Complete Order");
            System.out.println("5. Return to Cart to Continue Customizing");
            System.out.println("6. Cancel Order");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("\n--- Enter Customer Info ---");
                    System.out.println("(Type 0 or X at any time to cancel)");

                    String input;

                    // === NAME ===
                    System.out.println("Current name: " + (name != null ? name : "[Not set]"));
                    System.out.print("Enter new name (or press ENTER to keep current): ");
                    input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
                        System.out.println("Cancelled customer info entry.");
                        break;
                    } else if (!input.isBlank()) {
                        name = input; // Save immediately
                    }

                    // === CONTACT ===
                    System.out.println("Current contact: " + (contact != null ? contact : "[Not set]"));
                    System.out.print("Enter contact (or press ENTER to keep current): ");
                    input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
                        System.out.println("Cancelled customer info entry.");
                        break;
                    } else if (!input.isBlank()) {
                        contact = input; // Save immediately
                    }

                    // === EMAIL ===
                    System.out.println("Current email: " + (email != null ? email : "[Not set]"));
                    System.out.print("Enter email (or press ENTER to keep current): ");
                    input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
                        System.out.println("Cancelled customer info entry.");
                        break;
                    } else if (!input.isBlank()) {
                        email = input; // Save immediately
                    }

                    System.out.println("✅ Customer info updated.");
                }

                case "2" -> {
                    System.out.println("\n--- Select Payment Method ---");
                    System.out.println("(Type 0 or X to cancel)");

                    PaymentType[] types = PaymentType.values();

                    // Show current value
                    System.out.println("Current payment method: "
                            + (paymentMethod != null ? paymentMethod : "[Not set]"));

                    System.out.println("Press ENTER to keep current, or choose a new method:");

                    // List payment methods
                    for (int i = 0; i < types.length; i++) {
                        System.out.printf("%d. %s\n", i + 1, types[i]);
                    }

                    System.out.print("Enter number (or ENTER to keep current): ");
                    String input = sc.nextLine().trim();

                    // CANCEL
                    if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
                        System.out.println("Cancelled payment selection.");
                        break;
                    }

                    // KEEP CURRENT
                    if (input.isBlank()) {
                        if (paymentMethod == null) {
                            System.out.println("❗ No payment method set yet. Please choose one.");
                        } else {
                            System.out.println("Payment method unchanged.");
                        }
                        break;
                    }

                    // ATTEMPT TO SET NEW PAYMENT METHOD
                    try {
                        int sel = Integer.parseInt(input);
                        if (sel >= 1 && sel <= types.length) {
                            paymentMethod = types[sel - 1];
                            System.out.println("✅ Payment method updated.");
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }

                case "3" -> {
                    System.out.println("\n--- Add Notes ---");
                    System.out.println("(Type 0 or X to cancel)");

                    // Show current value
                    System.out.println("Current notes: " + (notes != null ? notes : "[None]"));
                    System.out.println("Press ENTER to keep current notes.");

                    System.out.print("Enter notes (or ENTER to keep current): ");
                    String input = sc.nextLine().trim();

                    // CANCEL
                    if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
                        System.out.println("Cancelled notes entry.");
                        break;
                    }

                    // KEEP CURRENT
                    if (input.isBlank()) {
                        System.out.println("Notes unchanged.");
                        break;
                    }

                    // UPDATE NOTES
                    notes = input;
                    System.out.println("✅ Notes updated.");
                }


                case "4" -> {
                    // Required field validation
                    if (name == null) {
                        System.out.println("❗ You must enter your name before checking out.");
                        break;
                    }
                    if (paymentMethod == null) {
                        System.out.println("❗ You must select a payment method.");
                        break;
                    }

                    // Build final objects
                    Customer customer = new Customer(
                            UUID.randomUUID().toString(),
                            name,
                            contact,
                            email
                    );

                    BigDecimal totalAmount = cart.calculateSubtotal();

                    BillingInfo billingInfo = new BillingInfo(
                            UUID.randomUUID().toString(),
                            customer,
                            paymentMethod,
                            totalAmount,
                            LocalDateTime.now(),
                            notes
                    );

                    Transaction transaction = new Transaction(
                            UUID.randomUUID().toString(),
                            cart.getItems(),
                            billingInfo
                    );

                    // Write receipt
                    ReceiptFileHandler receiptHandler = new ReceiptFileHandler();
                    receiptHandler.writeReceipt(transaction);

                    System.out.println("\n✅ Checkout complete!");
                    System.out.println("Transaction summary:");
                    System.out.println(transaction);
                    System.out.println("\nItems purchased:");
                    System.out.println(transaction.getItemSummary());

                    System.out.println("\nGenerating new Receipt...");
                    cart.clear();
                    System.out.println("\nReturning to main menu...");
                    return;
                }

                case "5" -> {
                    System.out.println("Returning to cart...");
                    controller.showCartScreen();
                    return; // exit checkout screen
                }

                case "6" -> {
                    System.out.println("Order cancelled. Cart cleared.");
                    cart.clear();
                    return; // exit checkout screen
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
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
