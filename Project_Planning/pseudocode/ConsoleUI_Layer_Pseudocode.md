<h1 align="center">======================= CONSOLE UI LAYER PSEUDOCODE =======================</h1>

---
## DIRECTORY

| Page # | Class Name                     |
|--------|--------------------------------|
| **1.** | ConsoleMainScreen              |
| **2.** | ConsoleOrderScreen             |
| **3.** | ConsoleBuildYourOwnRamenScreen |
| **4.** | ConsoleCartScreen              |
| **5.** | ConsoleCheckoutScreen          |
| **6.** | ConsoleTransactionLogScreen    |

---

## ---------------------------------

### 1. ConsoleMainScreen

## ---------------------------------

```
class ConsoleMainScreen
{
    // --- Methods ---
    
    display(): void
        // Show main menu options
        print("Welcome to Ichiraku Ramen Shop!")
        print("1. Place/New Order")
        print("2. Transaction Log")
        print("3. Exit")
        
        choice = getUserInput()
        
        if choice == 1
            ConsoleController.handleSignIn()
        else if choice == 2
            ConsoleController.promptAdminPassword()
        else if choice == 3
            exit application
        else
            print("Invalid selection. Please try again.")
            display()
}
```

---

## ---------------------------------

### 2. ConsoleOrderScreen

## ---------------------------------

```
class ConsoleOrderScreen
{
    // --- Methods ---
    
    display(cart: Cart): void
        // Show all item categories
        print("Select Category:")
        print("1. Ramen")
        print("2. Drinks")
        print("3. Appetizers")
        print("4. Desserts")
        print("5. View Cart")
        print("6. Checkout")
        print("7. Main Menu")
        
        choice = getUserInput()
        
        if choice == 1
            displayItems(ItemCategory.RAMEN, cart)
        else if choice == 2
            displayItems(ItemCategory.DRINK, cart)
        else if choice == 3
            displayItems(ItemCategory.APPETIZER, cart)
        else if choice == 4
            displayItems(ItemCategory.DESSERT, cart)
        else if choice == 5
            ConsoleCartScreen.display(cart)
        else if choice == 6
            ConsoleCheckoutScreen.display(cart)
        else if choice == 7
            ConsoleMainScreen.display()
        else
            print("Invalid selection. Try again.")
            display(cart)

    displayItems(category: ItemCategory, cart: Cart): void
        items = DataManager.getItemsByCategory(category)
        for i in 0..items.size()-1
            print((i+1) + ". " + items[i].getName() + " - $" + items[i].getPrice())
        print("Select item to add to cart or 0 to go back:")
        
        selection = getUserInput()
        if selection > 0 and selection <= items.size()
            selectedItem = items[selection-1]
            if selectedItem is Ramen
                ConsoleBuildYourOwnRamenScreen.display(selectedItem, cart)
            else
                cart.addItem(selectedItem)
            display(cart)
        else
            display(cart)
}
```

---

## ---------------------------------

### 3. ConsoleBuildYourOwnRamenScreen

## ---------------------------------

```
class ConsoleBuildYourOwnRamenScreen
{
    // --- Methods ---
    
    display(baseRamen: Ramen, cart: Cart): void
        // Show customization options
        print("Customize your ramen:")
        print("1. Select Broth")
        print("2. Select Noodles")
        print("3. Add/Remove Ingredients")
        print("4. Set Spice Level")
        print("5. Set Size")
        print("6. Add to Cart")
        print("7. Cancel")
        
        choice = getUserInput()
        
        switch choice
            case 1: selectBroth(baseRamen); display(baseRamen, cart)
            case 2: selectNoodles(baseRamen); display(baseRamen, cart)
            case 3: editIngredients(baseRamen); display(baseRamen, cart)
            case 4: setSpice(baseRamen); display(baseRamen, cart)
            case 5: setSize(baseRamen); display(baseRamen, cart)
            case 6: cart.addItem(baseRamen); ConsoleOrderScreen.display(cart)
            case 7: ConsoleOrderScreen.display(cart)
            default: print("Invalid choice"); display(baseRamen, cart)
}
```

---

## ---------------------------------

### 4. ConsoleCartScreen

## ---------------------------------

```
class ConsoleCartScreen
{
    // --- Methods ---
    
    display(cart: Cart): void
        print("Current Cart:")
        cartItems = cart.getItems()
        for i in 0..cartItems.size()-1
            print((i+1) + ". " + cartItems[i].toString())
        print("Total: $" + cart.calculateTotal())
        print("Options:")
        print("1. Remove Item")
        print("2. Update Quantity")
        print("3. Back to Order Menu")
        
        choice = getUserInput()
        
        if choice == 1
            removeItem(cart)
        else if choice == 2
            updateQuantity(cart)
        else
            ConsoleOrderScreen.display(cart)
}
```

---

## ---------------------------------

### 5. ConsoleCheckoutScreen

## ---------------------------------

```
class ConsoleCheckoutScreen
{
    // --- Methods ---
    
    display(cart: Cart): void
        if cart.isEmpty()
            print("Cart is empty. Cannot checkout.")
            ConsoleOrderScreen.display(cart)
        
        print("Proceeding to checkout...")
        print("Customer: " + ConsoleController.currentCustomer.getName())
        print("Billing Info: " + ConsoleController.currentCustomer.getBillingInfo())
        print("Total: $" + cart.calculateTotal())
        print("Confirm Purchase? (Y/N)")
        
        choice = getUserInput()
        if choice == "Y"
            transaction = new Transaction(cart, ConsoleController.currentCustomer, ConsoleController.currentCustomer.getBillingInfo())
            DataManager.saveTransaction(transaction)
            print("Purchase complete! Thank you.")
            ConsoleOrderScreen.display(new Cart())
        else
            ConsoleOrderScreen.display(cart)
}
```

---

## ---------------------------------

### 6. ConsoleTransactionLogScreen

## ---------------------------------

```
class ConsoleTransactionLogScreen
{
    // --- Methods ---
    
    display(transactionLog: TransactionLog): void
        print("Transaction Log:")
        for transaction in transactionLog.getTransactions()
            print(transaction.toString())
        
        print("Press any key to return to main menu.")
        getUserInput()
        ConsoleMainScreen.display()
}
```

---
