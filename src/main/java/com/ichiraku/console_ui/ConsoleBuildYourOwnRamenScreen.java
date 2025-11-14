package com.ichiraku.console_ui;

import com.ichiraku.controller.ConsoleController;
import com.ichiraku.enums.*;
import com.ichiraku.model.*;
import com.ichiraku.controller.DataManager;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleBuildYourOwnRamenScreen {

    private final ConsoleController controller;

    public ConsoleBuildYourOwnRamenScreen(ConsoleController controller) {
        this.controller = controller;
    }

    public void display() {
        Scanner sc = controller.getScanner();
        DataManager dataManager = controller.getDataManager();
        List<Ingredient> allIngredients = dataManager.getAllIngredients();

        System.out.println("\n=== 🍥 Build Your Own Ramen ===");

        // --- Step 1: Required selections ---
        Ingredient bowlSize = promptForIngredient(sc, allIngredients, IngredientCategory.BOWL_SIZE, "Choose a bowl size:");
        Ingredient broth = promptForIngredient(sc, allIngredients, IngredientCategory.BROTH, "Choose a broth:");
        Ingredient noodle = promptForIngredient(sc, allIngredients, IngredientCategory.NOODLE, "Choose a noodle type:");
        Ingredient spice = promptForIngredient(sc, allIngredients, IngredientCategory.SPICE_LEVEL, "Choose your spice level:");

        // --- Step 2: Optional ingredients ---
        List<Ingredient> extras = new ArrayList<>();
        boolean addingExtras = true;
        while (addingExtras) {
            System.out.println("\nWould you like to add any extras?");
            System.out.println("1. Add a topping/protein/sauce");
            System.out.println("2. Finish and build ramen");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                Ingredient extra = promptForIngredient(sc, allIngredients, null, "Select an extra ingredient:");
                if (extra != null) {
                    extras.add(extra);
                    System.out.println("✅ Added " + extra.getName());
                }
            } else if (choice.equals("2")) {
                addingExtras = false;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }

        // --- Step 3: Build ramen ---
        String ramenId = UUID.randomUUID().toString();
        Ramen.Builder builder = new Ramen.Builder(ramenId, "Custom Ramen", "A ramen built from scratch by the customer.")
                .setBowlSize(bowlSize)
                .setBroth(broth)
                .setNoodle(noodle)
                .setSpice(spice);

        for (Ingredient extra : extras) {
            builder.addIngredient(extra);
        }

        Ramen customRamen = builder.build();

        // --- Step 4: Add to cart ---
        controller.getCurrentCart().addItem(customRamen, 1);
        System.out.println("\n✅ Custom ramen added to your cart!");

        // --- Step 5: Navigation ---
        System.out.println("\nReturning to order menu...");
        //controller.showOrderScreen();
    }

    /**
     * Helper method for displaying ingredients by category and allowing user selection.
     */
    private Ingredient promptForIngredient(Scanner sc, List<Ingredient> allIngredients, IngredientCategory category, String prompt) {
        List<Ingredient> filtered;

        if (category == null) {
            // For extras: show all ingredients that are not core categories
            filtered = allIngredients.stream()
                    .filter(i -> i.getIngredientCategory() == IngredientCategory.PROTEIN
                            || i.getIngredientCategory() == IngredientCategory.TOPPING
                            || i.getIngredientCategory() == IngredientCategory.SAUCE
                            || i.getIngredientCategory() == IngredientCategory.SPICE)
                    .collect(Collectors.toList());
        } else {
            filtered = allIngredients.stream()
                    .filter(i -> i.getIngredientCategory() == category)
                    .collect(Collectors.toList());
        }

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No ingredients found for category: " + category);
            return null;
        }

        System.out.println("\n" + prompt);
        for (int i = 0; i < filtered.size(); i++) {
            Ingredient ing = filtered.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, ing.getName(), ing.getDescription());
        }
        System.out.print("Enter number: ");
        String input = sc.nextLine();

        try {
            int selection = Integer.parseInt(input);
            if (selection >= 1 && selection <= filtered.size()) {
                return filtered.get(selection - 1);
            }
        } catch (NumberFormatException ignored) {}
        System.out.println("Invalid choice. Skipping selection.");
        return null;
    }

    public void editRamenDetails(Cart cart, Ramen ramen, Scanner sc) {
        DataManager dataManager = controller.getDataManager();
        List<Ingredient> allIngredients = dataManager.getAllIngredients();

        // Track extras separately
        List<Ingredient> extras = new ArrayList<>(ramen.getCustomIngredients());

        // Track current selections for bowl, broth, noodles, spice
        Ingredient currentBowl = ramen.getBowlSize();
        Ingredient currentBroth = ramen.getBaseBroth();
        Ingredient currentNoodle = ramen.getNoodleType();
        Ingredient currentSpice = ramen.getSpiceLevel();

        boolean editing = true;

        while (editing) {
            System.out.println("\n=== Editing: " + ramen.getName() + " ===");
            System.out.println("1. Bowl Size: " + currentBowl.getName());
            System.out.println("2. Broth: " + currentBroth.getName());
            System.out.println("3. Noodles: " + currentNoodle.getName());
            System.out.println("4. Spice Level: " + currentSpice.getName());
            System.out.println("5. Extras:");
            if (extras.isEmpty()) {
                System.out.println("   (none)");
            } else {
                for (Ingredient ing : extras) {
                    System.out.println("   - " + ing.getName());
                }
            }
            System.out.println("6. Finish editing");
            System.out.print("Select an option to edit: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> {
                    Ingredient newBowl = promptForIngredient(sc, allIngredients, IngredientCategory.BOWL_SIZE, "Choose a bowl size:");
                    if (newBowl != null) currentBowl = newBowl;
                }
                case "2" -> {
                    Ingredient newBroth = promptForIngredient(sc, allIngredients, IngredientCategory.BROTH, "Choose a broth:");
                    if (newBroth != null) currentBroth = newBroth;
                }
                case "3" -> {
                    Ingredient newNoodle = promptForIngredient(sc, allIngredients, IngredientCategory.NOODLE, "Choose a noodle type:");
                    if (newNoodle != null) currentNoodle = newNoodle;
                }
                case "4" -> {
                    Ingredient newSpice = promptForIngredient(sc, allIngredients, IngredientCategory.SPICE_LEVEL, "Choose spice level:");
                    if (newSpice != null) currentSpice = newSpice;
                }
                case "5" -> editExtras(extras, sc, allIngredients);
                case "6" -> editing = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        // Build updated Ramen using Builder
        Ramen updatedRamen = new Ramen.Builder(ramen.getId(), ramen.getName(), ramen.getDescription())
                .setBowlSize(currentBowl)
                .setBroth(currentBroth)
                .setNoodle(currentNoodle)
                .setSpice(currentSpice)
                .addCustomIngredients(extras)
                .build();

        // Replace old Ramen in cart while keeping quantity
        int quantity = cart.getItems().get(ramen);
        cart.removeItem(ramen);
        cart.addItem(updatedRamen, quantity);

        System.out.println("✅ Ramen updated successfully!");
    }

    private void editExtras(List<Ingredient> extras, Scanner sc, List<Ingredient> allIngredients) {
        boolean editingExtras = true;

        while (editingExtras) {
            System.out.println("\n=== Extras Editing ===");
            if (extras.isEmpty()) {
                System.out.println("(none)");
            } else {
                for (int i = 0; i < extras.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, extras.get(i).getName());
                }
            }
            System.out.println("Options:");
            System.out.println("A. Add extra");
            System.out.println("R. Remove extra");
            System.out.println("F. Finish editing extras");
            System.out.print("Choice: ");

            String choice = sc.nextLine().toUpperCase();
            switch (choice) {
                case "A" -> {
                    Ingredient extra = promptForIngredient(sc, allIngredients, null, "Select an extra ingredient:");
                    if (extra != null && !extras.contains(extra)) {
                        extras.add(extra);
                        System.out.println("✅ Added " + extra.getName());
                    } else {
                        System.out.println("Ingredient already added or invalid.");
                    }
                }
                case "R" -> {
                    if (extras.isEmpty()) {
                        System.out.println("No extras to remove.");
                        break;
                    }
                    System.out.print("Enter number to remove: ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine()) - 1;
                        if (idx >= 0 && idx < extras.size()) {
                            System.out.println("✅ Removed " + extras.remove(idx).getName());
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
                case "F" -> editingExtras = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}

