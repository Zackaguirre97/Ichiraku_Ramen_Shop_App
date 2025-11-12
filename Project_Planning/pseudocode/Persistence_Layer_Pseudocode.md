<h1 align="center">========================== PERSISTENCE LAYER PSEUDOCODE ==========================</h1>

---
## DIRECTORY

| Page #  | Class Name                    |
|---------|-------------------------------|
| **1.**  | TransactionLogHandler         |
| **2.**  | CsvTransactionLogHandler      |
| **3.**  | DatabaseTransactionLogHandler |
| **4.**  | ItemLogHandler                |
| **5.**  | CsvItemLogHandler             |
| **6.**  | DatabaseItemLogHandler        |
| **7.**  | IngredientLogHandler          |
| **8.**  | CsvIngredientLogHandler       |
| **9.**  | DatabaseIngredientLogHandler  |
| **10.** | RecipeLogHandler              |
| **11.** | CsvRecipeLogHandler           |
| **12.** | DatabaseRecipeLogHandler      |
| **13.** | ReceiptFileHandler            |

---

## ---------------------------------

### 1. TransactionLogHandler *(interface)*

## ---------------------------------

```
interface TransactionLogHandler
{
    // --- Methods ---
    
    load(): TransactionLog
        // Loads all transactions from storage and returns a TransactionLog object
    
    save(transaction: Transaction): void
        // Saves a single transaction to storage
    
    saveAll(transactionLog: TransactionLog): void
        // Optional: saves entire transaction log
}
```

---

## ---------------------------------

### 2. CsvTransactionLogHandler *(implements TransactionLogHandler)*

## ---------------------------------

```
class CsvTransactionLogHandler implements TransactionLogHandler
{
    filePath: String                 // Path to CSV file
    
    // --- Constructor ---
    CsvTransactionLogHandler(filePath: String)
        this.filePath = filePath

    // --- Methods ---
    
    load(): TransactionLog
        transactions = new TransactionLog()
        csvLines = readCSV(filePath)
        for line in csvLines
            transaction = parseTransaction(line)
            transactions.add(transaction)
        return transactions

    save(transaction: Transaction): void
        csvLine = convertTransactionToCSV(transaction)
        appendLineToCSV(filePath, csvLine)

    saveAll(transactionLog: TransactionLog): void
        csvLines = []
        for transaction in transactionLog.getTransactions()
            csvLines.add(convertTransactionToCSV(transaction))
        writeCSV(filePath, csvLines)
}
```

---

## ---------------------------------

### 3. DatabaseTransactionLogHandler *(implements TransactionLogHandler)*

## ---------------------------------

```
class DatabaseTransactionLogHandler implements TransactionLogHandler
{
    dbConnection: DatabaseConnection
    
    // --- Constructor ---
    DatabaseTransactionLogHandler(dbConnection: DatabaseConnection)
        this.dbConnection = dbConnection

    // --- Methods ---
    
    load(): TransactionLog
        transactions = new TransactionLog()
        results = dbConnection.query("SELECT * FROM transactions")
        for row in results
            transaction = mapRowToTransaction(row)
            transactions.add(transaction)
        return transactions

    save(transaction: Transaction): void
        dbConnection.execute("INSERT INTO transactions ...", transaction.toDbParams())

    saveAll(transactionLog: TransactionLog): void
        for transaction in transactionLog.getTransactions()
            save(transaction)
}
```

---

## ---------------------------------

### 4. ItemLogHandler *(interface)*

## ---------------------------------

```
interface ItemLogHandler
{
    // --- Methods ---
    
    load(): List<Item>
        // Loads all items from storage and returns as a list
    
    save(item: Item): void
        // Saves a single item to storage
    
    saveAll(items: List<Item>): void
        // Saves all items at once
}
```

---

## ---------------------------------

### 5. CsvItemLogHandler *(implements ItemLogHandler)*

## ---------------------------------

```
class CsvItemLogHandler implements ItemLogHandler
{
    filePath: String                  // Path to CSV file containing item data
    
    // --- Constructor ---
    CsvItemLogHandler(filePath: String)
        this.filePath = filePath

    // --- Methods ---
    
    load(): List<Item>
        items = new List<Item>()
        csvLines = readCSV(filePath)
        for line in csvLines
            item = parseItem(line)
            items.add(item)
        return items

    save(item: Item): void
        csvLine = convertItemToCSV(item)
        appendLineToCSV(filePath, csvLine)

    saveAll(items: List<Item>): void
        csvLines = []
        for item in items
            csvLines.add(convertItemToCSV(item))
        writeCSV(filePath, csvLines)
}
```

---

## ---------------------------------

### 6. DatabaseItemLogHandler *(implements ItemLogHandler)*

## ---------------------------------

```
class DatabaseItemLogHandler implements ItemLogHandler
{
    dbConnection: DatabaseConnection
    
    // --- Constructor ---
    DatabaseItemLogHandler(dbConnection: DatabaseConnection)
        this.dbConnection = dbConnection

    // --- Methods ---
    
    load(): List<Item>
        items = new List<Item>()
        results = dbConnection.query("SELECT * FROM items")
        for row in results
            item = mapRowToItem(row)
            items.add(item)
        return items

    save(item: Item): void
        dbConnection.execute("INSERT INTO items ...", item.toDbParams())

    saveAll(items: List<Item>): void
        for item in items
            save(item)
}
```

---

## ---------------------------------

### 7. IngredientLogHandler *(interface)*

## ---------------------------------

```
interface IngredientLogHandler
{
    // --- Methods ---
    
    load(): List<Ingredient>
        // Loads all ingredients from storage
    
    save(ingredient: Ingredient): void
        // Saves a single ingredient to storage
    
    saveAll(ingredients: List<Ingredient>): void
        // Saves all ingredients at once
}
```

---

## ---------------------------------

### 8. CsvIngredientLogHandler *(implements IngredientLogHandler)*

## ---------------------------------

```
class CsvIngredientLogHandler implements IngredientLogHandler
{
    filePath: String                  // Path to CSV file containing ingredient data
    
    // --- Constructor ---
    CsvIngredientLogHandler(filePath: String)
        this.filePath = filePath

    // --- Methods ---
    
    load(): List<Ingredient>
        ingredients = new List<Ingredient>()
        csvLines = readCSV(filePath)
        for line in csvLines
            ingredient = parseIngredient(line)
            ingredients.add(ingredient)
        return ingredients

    save(ingredient: Ingredient): void
        csvLine = convertIngredientToCSV(ingredient)
        appendLineToCSV(filePath, csvLine)

    saveAll(ingredients: List<Ingredient>): void
        csvLines = []
        for ingredient in ingredients
            csvLines.add(convertIngredientToCSV(ingredient))
        writeCSV(filePath, csvLines)
}
```

---

## ---------------------------------
### 9. DatabaseIngredientLogHandler *(implements IngredientLogHandler)*
## ---------------------------------

```
class DatabaseIngredientLogHandler implements IngredientLogHandler
{
    dbConnection: DatabaseConnection
    
    // --- Constructor ---
    DatabaseIngredientLogHandler(dbConnection: DatabaseConnection)
        this.dbConnection = dbConnection

    // --- Methods ---
    
    load(): List<Ingredient>
        ingredients = new List<Ingredient>()
        results = dbConnection.query("SELECT * FROM ingredients")
        for row in results
            ingredient = mapRowToIngredient(row)
            ingredients.add(ingredient)
        return ingredients

    save(ingredient: Ingredient): void
        dbConnection.execute("INSERT INTO ingredients ...", ingredient.toDbParams())

    saveAll(ingredients: List<Ingredient>): void
        for ingredient in ingredients
            save(ingredient)
}
```

---

## ---------------------------------

### 10. RecipeLogHandler *(interface)*

## ---------------------------------

```
interface RecipeLogHandler
{
    // --- Methods ---
    
    load(): List<Recipe>
        // Loads all recipes from storage
    
    save(recipe: Recipe): void
        // Saves a single recipe to storage
    
    saveAll(recipes: List<Recipe>): void
        // Saves all recipes at once
}
```

---

## ---------------------------------

### 11. CsvRecipeLogHandler *(implements RecipeLogHandler)*

## ---------------------------------

```
class CsvRecipeLogHandler implements RecipeLogHandler
{
    filePath: String                  // Path to CSV file containing recipe data
    
    // --- Constructor ---
    CsvRecipeLogHandler(filePath: String)
        this.filePath = filePath

    // --- Methods ---
    
    load(): List<Recipe>
        recipes = new List<Recipe>()
        csvLines = readCSV(filePath)
        for line in csvLines
            recipe = parseRecipe(line)
            recipes.add(recipe)
        return recipes

    save(recipe: Recipe): void
        csvLine = convertRecipeToCSV(recipe)
        appendLineToCSV(filePath, csvLine)

    saveAll(recipes: List<Recipe>): void
        csvLines = []
        for recipe in recipes
            csvLines.add(convertRecipeToCSV(recipe))
        writeCSV(filePath, csvLines)
}
```

---

## ---------------------------------

### 12. DatabaseRecipeLogHandler *(implements RecipeLogHandler)*

## ---------------------------------

```
class DatabaseRecipeLogHandler implements RecipeLogHandler
{
    dbConnection: DatabaseConnection
    
    // --- Constructor ---
    DatabaseRecipeLogHandler(dbConnection: DatabaseConnection)
        this.dbConnection = dbConnection

    // --- Methods ---
    
    load(): List<Recipe>
        recipes = new List<Recipe>()
        results = dbConnection.query("SELECT * FROM recipes")
        for row in results
            recipe = mapRowToRecipe(row)
            recipes.add(recipe)
        return recipes

    save(recipe: Recipe): void
        dbConnection.execute("INSERT INTO recipes ...", recipe.toDbParams())

    saveAll(recipes: List<Recipe>): void
        for recipe in recipes
            save(recipe)
}
```

---

## ---------------------------------

### 13. ReceiptFileHandler

## ---------------------------------

```
class ReceiptFileHandler
{
    filePath: String                  // Path to store receipts
    
    // --- Constructor ---
    ReceiptFileHandler(filePath: String)
        this.filePath = filePath

    // --- Methods ---
    
    generateReceipt(transaction: Transaction): void
        receiptText = "----- Ichiraku Ramen Shop Receipt -----\n"
        receiptText += "Customer: " + transaction.getCustomer().getName() + "\n"
        receiptText += "Items:\n"
        for item in transaction.getCart().getItems()
            receiptText += "  - " + item.toString() + "\n"
        receiptText += "Total: $" + transaction.getCart().calculateTotal() + "\n"
        receiptText += "Payment Method: " + transaction.getBillingInfo().getPaymentMethod() + "\n"
        receiptText += "Transaction Date: " + transaction.getBillingInfo().getDate() + "\n"
        receiptText += "-------------------------------------\n"
        
        writeToLog(filePath, receiptText)
```

---
