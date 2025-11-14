package com.ichiraku.console_ui;

import com.ichiraku.controller.ConsoleController;
import com.ichiraku.model.*;
import com.ichiraku.controller.DataManager;

import java.util.List;
import java.util.Scanner;

public class ConsoleOrderScreen {

    private final ConsoleController controller;

    public ConsoleOrderScreen(ConsoleController controller) {
        this.controller = controller;
    }

    public void display() {
        Scanner sc = controller.getScanner();

        boolean ordering = true;
        while (ordering) {
            System.out.println("\n=== 🍜 Order Menu ===");
            System.out.println("1. Browse Ramen Options");
            System.out.println("2. Browse Appetizer Options");
            System.out.println("3. Browse Drink Options");
            System.out.println("4. Browse Dessert Options");
            System.out.println("5. View Cart");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showRamenCategoryMenu();
                case "2" -> showAppetizerMenu();
                case "3" -> showDrinkMenu();
                case "4" -> showDessertMenu();
                case "5" -> {
                    controller.showCartScreen(); // <-- directly go to Cart Menu
                    ordering = false; // leave Cart menu
                }
                case "6" -> ordering = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void showRamenCategoryMenu() {
        Scanner sc = controller.getScanner();

        boolean inRamenMenu = true;
        while (inRamenMenu) {
            System.out.println("\n=== 🍜 Ramen Options ===");
            System.out.println("1. View Signature Ramen Recipes");
            System.out.println("2. Build Your Own Ramen");
            System.out.println("3. Return to Order Menu");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showRamenMenu(); // your existing method
                case "2" -> controller.showBuildYourOwnRamen();
                case "3" -> inRamenMenu = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void showRamenMenu() {
        Scanner sc = controller.getScanner();
        List<Ramen> recipes = controller.getDataManager().getAllRecipes();
        Cart cart = controller.getCurrentCart();

        if (recipes.isEmpty()) {
            System.out.println("No ramen recipes available!");
            return;
        }

        System.out.println("\n=== Prebuilt Ramen Menu ===");
        for (int i = 0; i < recipes.size(); i++) {
            Ramen ramen = recipes.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, ramen.getName(), ramen.getDescription());
        }
        System.out.println((recipes.size() + 1) + ". Return to Order Menu");

        System.out.print("Select a ramen to add to cart: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= recipes.size()) {
                Ramen selectedRamen = recipes.get(selection - 1);
                cart.addItem(selectedRamen, 1);
                System.out.printf("✅ Added '%s' to cart!\n", selectedRamen.getName());
            } else if (selection == recipes.size() + 1) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // This needs to be copied and made into specific methods for Apps, Drinks, and Desserts.
    public void showItemMenu() {
        Scanner sc = controller.getScanner();
        List<Item> items = controller.getDataManager().getAllItems();
        Cart cart = controller.getCurrentCart();

        if (items.isEmpty()) {
            System.out.println("No items available!");
            return;
        }

        System.out.println("\n=== General Items Menu ===");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, item.getName(), item.getDescription());
        }
        System.out.println((items.size() + 1) + ". Return to Order Menu");

        System.out.print("Select an Item to add to cart: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item selectedItem = items.get(selection - 1);
                cart.addItem(selectedItem, 1);
                System.out.printf("✅ Added '%s' to cart!\n", selectedItem.getName());
            } else if (selection == items.size() + 1) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // This needs to be copied and made into specific methods for Apps, Drinks, and Desserts.
    public void showAppetizerMenu() {
        Scanner sc = controller.getScanner();
        List<Appetizer> items = controller.getDataManager().getAllAppetizers();
        Cart cart = controller.getCurrentCart();

        if (items.isEmpty()) {
            System.out.println("No items available!");
            return;
        }

        System.out.println("\n=== Appetizer Menu ===");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, item.getName(), item.getDescription());
        }
        System.out.println((items.size() + 1) + ". Return to Order Menu");

        System.out.print("Select an Item to add to cart: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item selectedItem = items.get(selection - 1);
                cart.addItem(selectedItem, 1);
                System.out.printf("✅ Added '%s' to cart!\n", selectedItem.getName());
            } else if (selection == items.size() + 1) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // This needs to be copied and made into specific methods for Apps, Drinks, and Desserts.
    public void showDrinkMenu() {
        Scanner sc = controller.getScanner();
        List<Drink> items = controller.getDataManager().getAllDrinks();
        Cart cart = controller.getCurrentCart();

        if (items.isEmpty()) {
            System.out.println("No items available!");
            return;
        }

        System.out.println("\n=== Drink Menu ===");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, item.getName(), item.getDescription());
        }
        System.out.println((items.size() + 1) + ". Return to Order Menu");

        System.out.print("Select an Item to add to cart: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item selectedItem = items.get(selection - 1);
                cart.addItem(selectedItem, 1);
                System.out.printf("✅ Added '%s' to cart!\n", selectedItem.getName());
            } else if (selection == items.size() + 1) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // This needs to be copied and made into specific methods for Apps, Drinks, and Desserts.
    public void showDessertMenu() {
        Scanner sc = controller.getScanner();
        List<Dessert> items = controller.getDataManager().getAllDesserts();
        Cart cart = controller.getCurrentCart();

        if (items.isEmpty()) {
            System.out.println("No items available!");
            return;
        }

        System.out.println("\n=== Dessert Menu ===");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, item.getName(), item.getDescription());
        }
        System.out.println((items.size() + 1) + ". Return to Order Menu");

        System.out.print("Select an Item to add to cart: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= items.size()) {
                Item selectedItem = items.get(selection - 1);
                cart.addItem(selectedItem, 1);
                System.out.printf("✅ Added '%s' to cart!\n", selectedItem.getName());
            } else if (selection == items.size() + 1) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}
