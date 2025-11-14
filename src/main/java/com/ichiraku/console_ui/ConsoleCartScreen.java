package com.ichiraku.console_ui;

import com.ichiraku.controller.ConsoleController;
import com.ichiraku.controller.DataManager;
import com.ichiraku.enums.IngredientCategory;
import com.ichiraku.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCartScreen {

    private final ConsoleController controller;

    public ConsoleCartScreen(ConsoleController controller) {
        this.controller = controller;
    }

    public void display() {
        Scanner sc = controller.getScanner();
        Cart cart = controller.getCurrentCart();

        boolean inCartMenu = true;
        while (inCartMenu) {
            System.out.println("\n=== 🛒 Your Cart ===");

            if (cart.getItems().isEmpty()) {
                System.out.println("Your cart is empty.");
            } else {
                int index = 1;
                List<Item> itemList = new ArrayList<>(cart.getItems().keySet());

                for (Item item : itemList) {
                    int quantity = cart.getItems().get(item);
                    BigDecimal totalPrice = item.calculatePrice()
                            .multiply(BigDecimal.valueOf(quantity))
                            .setScale(2, java.math.RoundingMode.HALF_UP);

                    System.out.printf("%d. %s x%d - $%s\n", index, item.getName(), quantity, totalPrice);

                    if (item instanceof Ramen ramen) {
                        System.out.println(ramen.getIngredientSummary());
                    }
                    index++;
                }

                System.out.println("Subtotal: $" + cart.calculateSubtotal());
            }

            // Menu options
            System.out.println("\nOptions:");
            System.out.println("1. Remove item");
            System.out.println("2. Update item quantity");
            System.out.println("3. Edit Ramen item");
            System.out.println("4. Checkout");
            System.out.println("5. Continue Ordering"); // go back to Order Menu
            System.out.println("6. Return to Main Menu");
            System.out.print("Select an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> removeItem(cart, sc);
                case "2" -> updateQuantity(cart, sc);
                case "3" -> {
                    if (cart.getItems().isEmpty()) {
                        System.out.println("Cart is empty, nothing to edit.");
                        break;
                    }
                    Ramen selected = selectRamenFromCart(cart, sc);
                    ConsoleBuildYourOwnRamenScreen byorScreen = new ConsoleBuildYourOwnRamenScreen(controller);
                    if (selected != null) {
                        byorScreen.editRamenDetails(cart, selected, sc);
                    }
                    else {
                        System.out.println("No Ramen selected or invalid choice.");
                    }
                }
                case "4" -> {
                    if (!cart.getItems().isEmpty()) {
                        controller.showCheckoutScreen();
                        inCartMenu = false;
                    } else {
                        System.out.println("Cart is empty, cannot checkout.");
                    }
                }
                case "5" -> {
                    controller.showOrderScreen();
                    inCartMenu = false;
                }
                case "6" -> inCartMenu = false;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    // --- Remove an item from cart ---
    private void removeItem(Cart cart, Scanner sc) {
        List<Item> items = new ArrayList<>(cart.getItems().keySet());
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Nothing to remove.");
            return;
        }

        System.out.print("Enter the number of the item to remove: ");
        String input = sc.nextLine();
        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item itemToRemove = items.get(selection - 1);
                cart.removeItem(itemToRemove);
                System.out.println("✅ Removed " + itemToRemove.getName() + " from cart.");
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // --- Update quantity of an item ---
    private void updateQuantity(Cart cart, Scanner sc) {
        List<Item> items = new ArrayList<>(cart.getItems().keySet());
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Nothing to update.");
            return;
        }

        System.out.print("Enter the number of the item to update: ");
        String input = sc.nextLine();
        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item itemToUpdate = items.get(selection - 1);
                System.out.print("Enter new quantity (0 to remove): ");
                int newQty = Integer.parseInt(sc.nextLine());
                cart.updateQuantity(itemToUpdate, newQty);
                System.out.println("✅ Updated quantity for " + itemToUpdate.getName());
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    /**
     * Helper method to select a Ramen item from the cart.
     * Returns null if no Ramen items are in the cart or user cancels.
     */
    private Ramen selectRamenFromCart(Cart cart, Scanner sc) {
        List<Ramen> ramens = cart.getItems().keySet().stream()
                .filter(item -> item instanceof Ramen)
                .map(item -> (Ramen) item)
                .toList();

        if (ramens.isEmpty()) {
            System.out.println("No Ramen items in the cart.");
            return null;
        }

        System.out.println("\nSelect a Ramen to edit:");
        for (int i = 0; i < ramens.size(); i++) {
            Ramen r = ramens.get(i);
            System.out.printf("%d. %s\n", i + 1, r.getName());
        }
        System.out.print("Enter number (or 0 to cancel): ");

        try {
            int selection = Integer.parseInt(sc.nextLine());
            if (selection == 0) return null;
            if (selection >= 1 && selection <= ramens.size()) {
                return ramens.get(selection - 1);
            }
        } catch (NumberFormatException ignored) { }
        System.out.println("Invalid choice.");
        return null;
    }
}
