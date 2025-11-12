<h1 align="center">======================= MODEL LAYER PSEUDOCODE =======================</h1>

---
## DIRECTORY

| Page #  | Class Name     |
|---------|----------------|
| **1.**  | Item           |
| **2.**  | Ramen          |
| **3.**  | Drink          |
| **4.**  | Appetizer      |
| **5.**  | Dessert        |
| **6.**  | Ingredient     |
| **7.**  | Recipe         |
| **8.**  | Cart           |
| **9.**  | Customer       |
| **10.** | BillingInfo    |
| **11.** | Transaction    |
| **12.** | TransactionLog |

---

## ---------------------------------

### 1. Abstract Class: Item

## ---------------------------------

```
abstract class Item
{
    // --- Fields / Attributes ---
    id: String                // Unique identifier for the item
    name: String              // Name of the item (e.g., "Shoyu Ramen")
    description: String       // Short description of the item
    price: BigDecimal         // Base price of the item
    category: ItemCategory    // Enum defining the category (Ramen, Drink, Appetizer, Dessert)

    // --- Constructor ---
    Item(id: String, name: String, description: String, price: BigDecimal, category: ItemCategory)
        this.id = id
        this.name = name
        this.description = description
        this.price = price
        this.category = category

    // --- Abstract Methods ---
    abstract calculatePrice(): BigDecimal
        // Each subclass may override this if price depends on customization

    // --- Concrete Methods ---
    getName(): String
        return this.name

    getDescription(): String
        return this.description

    getPrice(): BigDecimal
        return this.price

    getCategory(): ItemCategory
        return this.category

    toString(): String
        // Returns a formatted string with item details
        return name + " (" + category + "): $" + price
}
```

---

## -----------------------------------------------------------

### 2. Derived[Item] Class: Ramen (Builder)

## -----------------------------------------------------------

```
class Ramen extends Item
{
    // --- Fields / Attributes ---
    baseBroth: BrothType                  // Enum representing the type of broth (Shoyu, Miso, Tonkotsu, Shio, etc.)
    noodleType: NoodleType                // Enum representing the type of noodles (Thin, Thick, Wavy, etc.)
    customIngredients: List<Ingredient>   // Optional list of added or removed ingredients
    spiceLevel: Int                       // Integer (0–5) representing spice intensity
    size: RamenSize                       // Enum (Small, Regular, Large)
    basePrice: BigDecimal                 // Default ramen price before customizations

    // --- Constructor (private, only Builder can call) ---
    Ramen(builder: Builder)
        super(builder.id, builder.name, builder.description, builder.basePrice, ItemCategory.RAMEN)
        this.baseBroth = builder.baseBroth
        this.noodleType = builder.noodleType
        this.customIngredients = builder.customIngredients
        this.spiceLevel = builder.spiceLevel
        this.size = builder.size
        this.basePrice = builder.basePrice

    // --- Methods ---
    calculatePrice(): BigDecimal
        total = basePrice

        // Adjust for size
        if size == RamenSize.LARGE
            total = total.add(new BigDecimal("2.00"))
        else if size == RamenSize.SMALL
            total = total.subtract(new BigDecimal("1.00"))

        // Add cost of extra ingredients
        for ingredient in customIngredients
            total = total.add(ingredient.getPrice())

        return total

    toString(): String
        return name + " [" + baseBroth + " | " + noodleType + " | Spice: " + spiceLevel +
               "] $" + calculatePrice()

    // --- Nested Builder Class ---
    class Builder
    {
        // --- Builder Fields ---
        id: String                           // Unique identifier for the ramen
        name: String                         // Name of the ramen (e.g., "Shoyu Ramen")
        description: String                  // Short description
        baseBroth: BrothType                 // Selected broth type
        noodleType: NoodleType               // Selected noodle type
        customIngredients: List<Ingredient> = new List<Ingredient>()  // Ingredients to add/remove
        spiceLevel: Int = 0                  // Default spice level
        size: RamenSize = RamenSize.REGULAR  // Default size
        basePrice: BigDecimal                // Base price

        // --- Builder Constructor ---
        Builder(id: String, name: String, description: String, basePrice: BigDecimal)
            this.id = id
            this.name = name
            this.description = description
            this.basePrice = basePrice

        // --- Builder Methods (Chained) ---
        setBroth(broth: BrothType): Builder
            this.baseBroth = broth
            return this

        setNoodle(noodle: NoodleType): Builder
            this.noodleType = noodle
            return this

        addIngredient(ingredient: Ingredient): Builder
            customIngredients.add(ingredient)
            return this

        removeIngredient(ingredient: Ingredient): Builder
            customIngredients.remove(ingredient)
            return this

        setSpice(level: Int): Builder
            if level >= 0 and level <= 5
                this.spiceLevel = level
            else
                print("Invalid spice level")
            return this

        setSize(size: RamenSize): Builder
            this.size = size
            return this

        build(): Ramen
            return new Ramen(this)
    }
}
```

---

## -------------------------------------------

### 3. Class: Drink (extends Item)

## -------------------------------------------

```
class Drink extends Item
{
    // --- Fields / Attributes ---
    size: DrinkSize          // Enum (Small, Medium, Large)
    isCold: Boolean          // True if the drink is served cold
    basePrice: BigDecimal    // Base price of the drink

    // --- Constructor ---
    Drink(id: String, name: String, description: String, size: DrinkSize, isCold: Boolean, basePrice: BigDecimal)
        super(id, name, description, basePrice, ItemCategory.DRINK)
        this.size = size
        this.isCold = isCold
        this.basePrice = basePrice

    // --- Methods ---
    calculatePrice(): BigDecimal
        total = basePrice

        // Adjust price based on size
        if size == DrinkSize.MEDIUM
            total = total.add(new BigDecimal("0.50"))
        else if size == DrinkSize.LARGE
            total = total.add(new BigDecimal("1.00"))

        return total

    toString(): String
        return name + " [" + size + " | " + (isCold ? "Cold" : "Hot") + "] $" + calculatePrice()
}
```

---

## -----------------------------------------------

### 4. Class: Appetizer (extends Item)

## -----------------------------------------------

```
class Appetizer extends Item
{
    // --- Fields / Attributes ---
    portionSize: PortionSize      // Enum (Small, Regular, Large)
    isVegetarian: Boolean         // True if vegetarian
    basePrice: BigDecimal         // Base price

    // --- Constructor ---
    Appetizer(id: String, name: String, description: String, portionSize: PortionSize, isVegetarian: Boolean, basePrice: BigDecimal)
        super(id, name, description, basePrice, ItemCategory.APPETIZER)
        this.portionSize = portionSize
        this.isVegetarian = isVegetarian
        this.basePrice = basePrice

    // --- Methods ---
    calculatePrice(): BigDecimal
        total = basePrice

        // Adjust for portion size
        if portionSize == PortionSize.LARGE
            total = total.add(new BigDecimal("2.00"))
        else if portionSize == PortionSize.SMALL
            total = total.subtract(new BigDecimal("1.00"))

        return total

    toString(): String
        return name + " [" + portionSize + (isVegetarian ? " | Vegetarian" : "") + "] $" + calculatePrice()
}
```

---

## --------------------------------------------

### 5. Class: Dessert (extends Item)

## --------------------------------------------

```
class Dessert extends Item
{
    // --- Fields / Attributes ---
    flavor: String             // e.g., "Matcha", "Chocolate"
    isIceCream: Boolean        // True if dessert is ice cream
    basePrice: BigDecimal      // Base price

    // --- Constructor ---
    Dessert(id: String, name: String, description: String, flavor: String, isIceCream: Boolean, basePrice: BigDecimal)
        super(id, name, description, basePrice, ItemCategory.DESSERT)
        this.flavor = flavor
        this.isIceCream = isIceCream
        this.basePrice = basePrice

    // --- Methods ---
    calculatePrice(): BigDecimal
        return basePrice

    toString(): String
        return name + " [" + flavor + (isIceCream ? " | Ice Cream" : "") + "] $" + calculatePrice()
}
```

---

## -------------------------------------------------

### 6. Class: Ingredient (extends Item)

## -------------------------------------------------

```
class Ingredient extends Item
{
    // --- Fields / Attributes ---
    ingredientCategory: IngredientCategory    // Enum (BROTH, NOODLE, TOPPING, etc.)
    associatedItemId: String                  // Optional: the ID of the menu item or recipe this ingredient belongs to
    basePrice: BigDecimal                     // Base price of ingredient (used for calculating additions to ramen, etc.)

    // --- Constructor ---
    Ingredient(id: String, name: String, description: String, category: ItemCategory, ingredientCategory: IngredientCategory, basePrice: BigDecimal)
        super(id, name, description, basePrice, category)
        this.ingredientCategory = ingredientCategory
        this.associatedItemId = null
        this.basePrice = basePrice

    // --- Methods ---
    calculatePrice(): BigDecimal
        return basePrice

    toString(): String
        return name + " [" + ingredientCategory + "] $" + basePrice
}
```

---

## -------------------------------------------------------

### 7. Class: Recipe
*(Represents a preconfigured menu item — usually a Ramen base)*

## -------------------------------------------------------

```
class Recipe
{
    // --- Fields / Attributes ---
    id: String                    // Unique identifier for the recipe
    name: String                  // Display name (e.g., "Tonkotsu Classic")
    baseItem: Item                // Reference to the base item (e.g., a Ramen object)
    defaultIngredients: List<Ingredient> // Default ingredients included
    description: String           // Optional description (e.g., "Rich pork broth with garlic oil")
    isActive: Boolean             // Whether this recipe is currently available

    // --- Constructor ---
    Recipe(id: String, name: String, baseItem: Item, defaultIngredients: List<Ingredient>, description: String, isActive: Boolean)
        this.id = id
        this.name = name
        this.baseItem = baseItem
        this.defaultIngredients = defaultIngredients
        this.description = description
        this.isActive = isActive

    // --- Methods ---
    getBaseItem(): Item
        return baseItem

    getDefaultIngredients(): List<Ingredient>
        return defaultIngredients

    activate()
        isActive = true

    deactivate()
        isActive = false

    toString(): String
        return "Recipe: " + name + " | Active: " + isActive
}
```

---

## -----------------------------------------

### 8. Class: Cart
*(Represents the user’s current order selection)*

## -----------------------------------------

```
class Cart
{
    // --- Fields / Attributes ---
    items: Map<Item, Int>        // Tracks item objects and their quantities

    // --- Constructor ---
    Cart()
        items = new Map<Item, Int>()

    // --- Methods ---
    addItem(item: Item, quantity: Int)
        if items.contains(item)
            items[item] += quantity
        else
            items[item] = quantity

    removeItem(item: Item)
        if items.contains(item)
            items.remove(item)

    updateQuantity(item: Item, newQuantity: Int)
        if items.contains(item)
            items[item] = newQuantity

    calculateSubtotal(): Double
        subtotal = 0.0
        for each (item, qty) in items:
            subtotal += item.calculatePrice() * qty
        return subtotal

    clear()
        items.clear()

    getItems(): Map<Item, Int>
        return items

    toString(): String
        return "Cart (" + items.size() + " items) | Subtotal: $" + calculateSubtotal()
}
```

---

## ---------------------------------

### 9. Class: Customer

## ---------------------------------

```
class Customer
{
    // --- Fields / Attributes ---
    id: String                 // Unique customer identifier (e.g., "CUST-00123" or generated UUID)
    name: String               // Full name of the customer (e.g., "Naruto Uzumaki")
    favoriteItem: String       // Name of customer’s favorite ramen or menu item
    loyaltyLevel: Int          // Optional loyalty or visit count indicator
    contactNumber: String      // Optional contact number (for receipts or delivery)
    email: String              // Optional email address (could be null for guests)

    // --- Constructor ---
    Customer(id: String, name: String, favoriteItem: String,
             loyaltyLevel: Int, contactNumber: String, email: String)
        this.id = id
        this.name = name
        this.favoriteItem = favoriteItem
        this.loyaltyLevel = loyaltyLevel
        this.contactNumber = contactNumber
        this.email = email

    // --- Overloaded Constructor (for simple Guest or Random Customers) ---
    Customer(id: String, name: String, favoriteItem: String)
        this(id, name, favoriteItem, 0, null, null)

    // --- Methods ---
    getId(): String
        return this.id

    getName(): String
        return this.name

    getFavoriteItem(): String
        return this.favoriteItem

    getLoyaltyLevel(): Int
        return this.loyaltyLevel

    incrementLoyalty()
        this.loyaltyLevel += 1

    getContactNumber(): String
        return this.contactNumber

    getEmail(): String
        return this.email

    toString(): String
        return "Customer: " + name + 
               (favoriteItem != null ? " | Favorite: " + favoriteItem : "") +
               (loyaltyLevel > 0 ? " | Loyalty Level: " + loyaltyLevel : "")
}
```

---

## ------------------------------------------------

### 10. Class: BillingInfo
*(Captures payment details and total cost for a transaction)*

## ------------------------------------------------

```
class BillingInfo
{
    // --- Fields / Attributes ---
    billingId: String           // Unique identifier for this billing record
    customerName: String        // Name of the paying customer
    paymentMethod: PaymentType  // Enum (CASH, CARD, ONLINE, etc.)
    amountPaid: BigDecimal      // Total paid amount
    transactionDate: DateTime   // Timestamp of payment
    notes: String               // Optional notes (e.g., "Used coupon", "Partial refund")

    // --- Constructor ---
    BillingInfo(billingId: String, customerName: String, paymentMethod: PaymentType,
                amountPaid: BigDecimal, transactionDate: DateTime, notes: String)
        this.billingId = billingId
        this.customerName = customerName
        this.paymentMethod = paymentMethod
        this.amountPaid = amountPaid
        this.transactionDate = transactionDate
        this.notes = notes

    // --- Methods ---
    getAmountPaid(): BigDecimal
        return amountPaid

    getPaymentSummary(): String
        return "Paid " + amountPaid + " via " + paymentMethod + " on " + transactionDate

    toString(): String
        return "Billing[" + billingId + "] " + customerName + " | " + getPaymentSummary()
}
```

---

## ------------------------------------------------

### 11. Class: Transaction
*(Represents a completed order — links Cart + BillingInfo)*

## ------------------------------------------------

```
class Transaction
{
    // --- Fields / Attributes ---
    transactionId: String          // Unique identifier for this transaction
    cartItems: List<CartItem>      // List of purchased items with quantities/customizations
    billingInfo: BillingInfo       // Associated billing record
    transactionDate: DateTime      // Timestamp of checkout
    totalAmount: BigDecimal        // Cached total of all items in cart
    status: TransactionStatus      // Enum (PENDING, COMPLETED, REFUNDED, CANCELLED)

    // --- Constructor ---
    Transaction(transactionId: String, cartItems: List<CartItem>, billingInfo: BillingInfo)
        this.transactionId = transactionId
        this.cartItems = cartItems
        this.billingInfo = billingInfo
        this.transactionDate = getCurrentDateTime()
        this.totalAmount = calculateTotal()
        this.status = TransactionStatus.COMPLETED

    // --- Methods ---
    calculateTotal(): BigDecimal
        total = BigDecimal(0)
        for each cartItem in cartItems
            total += cartItem.getItem().calculatePrice() * cartItem.getQuantity()
        return total

    getItemSummary(): String
        summary = ""
        for each cartItem in cartItems
            summary += cartItem.getItem().getName() + " x" + cartItem.getQuantity() + "\n"
        return summary

    markRefunded()
        status = TransactionStatus.REFUNDED

    toString(): String
        return "Transaction[" + transactionId + "] $" + totalAmount + " | " + billingInfo.getPaymentSummary()
}
```

---

## -------------------------------------------------

### 12. Class: TransactionLog
*(Maintains a historical record of all past transactions)*

## -------------------------------------------------

```
class TransactionLog
{
    // --- Fields / Attributes ---
    transactions: List<Transaction>     // Record of all transactions

    // --- Constructor ---
    TransactionLog()
        transactions = new List<Transaction>()

    // --- Methods ---
    addTransaction(transaction: Transaction)
        transactions.add(transaction)

    getTransactionById(id: String): Transaction
        for each txn in transactions
            if txn.transactionId == id
                return txn
        return null

    getTransactionsByDate(date: Date): List<Transaction>
        results = new List<Transaction>()
        for each txn in transactions
            if txn.transactionDate.dateOnly() == date
                results.add(txn)
        return results

    calculateTotalSales(): BigDecimal
        total = BigDecimal(0)
        for each txn in transactions
            total += txn.totalAmount
        return total

    toString(): String
        return "TransactionLog: " + transactions.size() + " total | $" + calculateTotalSales()
}
```

---
```