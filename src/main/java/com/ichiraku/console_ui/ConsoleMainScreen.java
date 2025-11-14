package com.ichiraku.console_ui;

import com.ichiraku.controller.ConsoleController;
import java.util.Scanner;

public class ConsoleMainScreen {

    private final ConsoleController controller;

    public ConsoleMainScreen(ConsoleController controller) {
        this.controller = controller;
    }

    public void display() {
        Scanner sc = controller.getScanner();
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Place Order");
        System.out.println("2. View Cart");
        System.out.println("3. Checkout");
        System.out.println("4. Exit");

        System.out.print("Select an option: ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1" -> controller.showOrderScreen();
            case "2" -> controller.showCartScreen();
            case "3" -> controller.showCheckoutScreen();
            case "4" -> {
                controller.exitApp();
                return;
            }
            default -> System.out.println("Invalid option, please try again.");
        }
    }
}
