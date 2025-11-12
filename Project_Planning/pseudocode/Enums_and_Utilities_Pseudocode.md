<h1 align="center">======================= ENUMS & UTILITIES PSEUDOCODE =======================</h1>

---
## DIRECTORY

| Page #  | Class Name         |
|---------|--------------------|
| **1.**  | ItemCategory       |
| **2.**  | IngredientCategory |
| **3.**  | RecipeCategory     |
| **4.**  | TransactionStatus  |
| **5.**  | CustomerGenerator  |

---
## ---------------------------------

### 1. ItemCategory

## ---------------------------------

```
enum ItemCategory
{
    RAMEN
    DRINK
    APPETIZER
    DESSERT
    INGREDIENT
}
```

---

## ---------------------------------

### 2. IngredientCategory

## ---------------------------------

```
enum IngredientCategory
{
    TOPPING
    PROTEIN
    VEGETABLE
    SAUCE
    OTHER
}
```

---

## ---------------------------------

### 3. RecipeCategory

## ---------------------------------

```
enum RecipeCategory
{
    CLASSIC
    SPECIAL
    SEASONAL
}
```

---

## ---------------------------------

### 4. TransactionStatus

## ---------------------------------

```
enum TransactionStatus
{
    PENDING
    COMPLETED
    CANCELLED
    REFUNDED
}
```

---

## ---------------------------------

### 5. CustomerGenerator (Utility Class)

## ---------------------------------

```
class CustomerGenerator
{
    // --- Fields ---
    predefinedCustomers: List<Customer> = [
        new Customer("Naruto Uzumaki", new BillingInfo(...)),
        new Customer("Sasuke Uchiha", new BillingInfo(...)),
        new Customer("Sakura Haruno", new BillingInfo(...)),
        new Customer("Jiraiya Sensei", new BillingInfo(...)),
        new Customer("Shikamaru Nara", new BillingInfo(...))
    ]

    // --- Methods ---
    
    getRandomCustomer(): Customer
        index = random(0, predefinedCustomers.size()-1)
        return predefinedCustomers[index]
}
```

---
