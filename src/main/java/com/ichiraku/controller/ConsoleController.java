package com.ichiraku.controller;

import com.ichiraku.model.*;
import com.ichiraku.console_ui.*;

import java.util.Scanner;

public class ConsoleController {

    private final DataManager dataManager;
    private final Scanner scanner;

    // Session state
    private Cart currentCart;
    private Customer currentCustomer;
    private boolean running = true;

    // Screens
    private ConsoleMainScreen mainScreen;
    private ConsoleOrderScreen orderScreen;
    private ConsoleBuildYourOwnRamenScreen buildScreen;
    private ConsoleCartScreen cartScreen;
    private ConsoleCheckoutScreen checkoutScreen;

    // --- Constructor ---
    public ConsoleController() {
        this.dataManager = new DataManager();
        this.scanner = new Scanner(System.in);

        // Initialize session
        this.currentCart = new Cart();

        // Initialize screens (passing shared data)
        this.mainScreen = new ConsoleMainScreen(this);
        this.orderScreen = new ConsoleOrderScreen(this);
        this.buildScreen = new ConsoleBuildYourOwnRamenScreen(this);
        this.cartScreen = new ConsoleCartScreen(this);
        this.checkoutScreen = new ConsoleCheckoutScreen(this);
    }

    // --- Main Loop ---
    public void start() {
        System.out.println("🍜 Welcome to Ichiraku Ramen!");
        while (running) {
            mainScreen.display(); // starts from main screen
        }
        System.out.println("\nArigato! See you again soon 🍥");
    }

    // --- Navigation Helpers ---
    public void showMainMenu() { mainScreen.display(); }
    public void showOrderScreen() { orderScreen.display(); }
    public void showBuildYourOwnRamen() { buildScreen.display(); }
    public void showCartScreen() { cartScreen.display(); }
    public void showCheckoutScreen() { checkoutScreen.display(); }

    public void exitApp() { running = false; }

    // --- Shared Data Accessors ---
    public DataManager getDataManager() { return dataManager; }
    public Cart getCurrentCart() { return currentCart; }
    public void setCurrentCart(Cart cart) { this.currentCart = cart; }
    public Scanner getScanner() { return scanner; }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public void setCurrentCustomer(Customer customer) { this.currentCustomer = customer; }
}

