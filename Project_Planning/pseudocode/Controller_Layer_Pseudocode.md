<h1 align="center">======================= CONTROLLER LAYER PSEUDOCODE =======================</h1>

---
## DIRECTORY

| Page # | Class Name        |
|--------|-------------------|
| **1.** | ConsoleController |
| **2.** | DataManager       |

---

## ---------------------------------

### 1. ConsoleController

## ---------------------------------
```
class ConsoleController
{
    // --- Fields / Attributes ---
    dataManager: DataManager            // Handles data persistence and retrieval
    currentCustomer: Customer           // Customer currently placing an order
    currentCart: Cart                   // Cart associated with the current order

    // --- Constructor ---
    ConsoleController(dataManager: DataManager)
        this.dataManager = dataManager
        this.currentCustomer = null
        this.currentCart = null

    // --- Methods ---
    
    startApp(): void
        // Entry point for the console app
        displayMainMenu()

    displayMainMenu(): void
        // Presents user with main menu options
        // Options: Place/New Order, Transaction Log, Exit
        // Handles selection
        if user selects "Place/New Order"
            handleSignIn()
        else if user selects "Transaction Log"
            promptAdminPassword()
        else if user selects "Exit"
            exit application

    handleSignIn(): void
        // Handles customer sign-in for a new order
        // For this app, triggers CustomerGenerator
        this.currentCustomer = CustomerGenerator.getRandomCustomer()
        this.currentCart = new Cart()
        navigateToOrderScreen()

    promptAdminPassword(): void
        // Prompt user for admin password before accessing Transaction Log
        if entered password == ADMIN_PASSWORD
            navigateToTransactionLogScreen()
        else
            print("Incorrect password. Access denied.")

    navigateToOrderScreen(): void
        // Routes to the order screen UI
        ConsoleOrderScreen.display(currentCart)

    navigateToTransactionLogScreen(): void
        // Routes to transaction log UI
        ConsoleTransactionLogScreen.display(dataManager.loadTransactionLog())
}
```

---

## ---------------------------------

### 2. DataManager

## ---------------------------------

```
class DataManager
{
    // --- Fields / Attributes ---
    items: List<Item>                         // All menu items (ramen, drinks, desserts, etc.)
    ingredients: List<Ingredient>             // All ingredients for custom orders
    recipes: List<Recipe>                     // Prebuilt recipes
    transactionLog: TransactionLog            // Stores all completed transactions

    // --- Constructor ---
    DataManager()
        this.items = loadItems()
        this.ingredients = loadIngredients()
        this.recipes = loadRecipes()
        this.transactionLog = loadTransactionLog()

    // --- Methods ---

    loadItems(): List<Item>
        // Load items from CSV or database
        return ItemFileHandler.load()

    loadIngredients(): List<Ingredient>
        // Load ingredients from CSV or database
        return IngredientFileHandler.load()

    loadRecipes(): List<Recipe>
        // Load recipes from CSV or database
        return RecipeFileHandler.load()

    loadTransactionLog(): TransactionLog
        // Load transaction log from CSV or database
        return TransactionLogHandler.load()

    saveTransaction(transaction: Transaction): void
        // Save completed transaction
        transactionLog.add(transaction)
        TransactionLogHandler.save(transaction)
}
```

---
