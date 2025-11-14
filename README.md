# 📄 **Ichiraku Ramen Ordering System — Project Write-Up**

## **1. Introduction**

The Ichiraku Ramen Ordering System is a console-based point-of-sale application designed to simulate a ramen shop ordering workflow. Customers can build fully customized ramen bowls, add drinks and sides, review their order, and complete checkout. Upon confirmation, the system generates a receipt file named using a timestamp (`yyyyMMdd-hhmmss.txt`).

This write-up describes the application’s features, explains how each requirement is met, and highlights the implementation decisions made to ensure clean and extensible software design.

---

## **2. Requirements Fulfillment**

Below is a detailed breakdown showing how each required feature is implemented in the final application.

---

### **Home Screen**

**Requirement:**

* Provide “New Order” and “Exit” options
* Continue running until user chooses Exit

**Implementation:**

* `ConsoleMainScreen.display()` presents the main menu with:

  1. Place Order
  2. View Cart
  3. Checkout
  4. Exit
* Application loops until the user selects Exit
* Controller manages screen transitions cleanly

**Status:** ✔ Fully implemented
**Enhancement:** Added access to *cart* and *checkout* directly from Home.

---

### **Order Screen**

**Requirement:**

* Options to Add Item, Add Drink, Add Main Side, Checkout, Cancel

**Implementation:**

* `ConsoleOrderScreen` contains all required order actions:
  ✔ Prebuilt Ramen
  ✔ Build Your Own Ramen (customer-built item with customizable toppings)
  ✔ Return to Home
* “Cancel Order” is implemented by clearing the `Cart` and returning to Home

**Status:** ✔ Fully implemented
**Enhancement:** Added *prebuilt ramen recipes* for convenience.

---

### **Add Item (Core Item Customization)**

**Requirement:**

* Must guide user through type → size → toppings → special option
* Premium toppings must add extra cost
* Allow extras
* Must support specialization option

**Implementation:**

* Core item: **Ramen**
* Built through `ConsoleBuildYourOwnRamenScreen`
* Customization steps:
  ✔ Size (small/medium/large)
  ✔ Broth
  ✔ Noodles
  ✔ Spice Level
  ✔ Proteins
  ✔ Toppings (regular & premium)
  ✔ Special Options (via ingredient category)
* Pricing:
  ✔ Ingredients have individual costs
  ✔ Premium toppings cost more
  ✔ Extras multiply cost

**Status:** ✔ Fully implemented
**Enhancement:** Expanded beyond requirements to allow:

* Multiple ingredient categories
* Spice level as a selectable ingredient
* CSV-driven ingredient loading

---

### **Add Drink**

**Requirement:**

* Select size & flavor

**Implementation:**

* Drinks loaded via CSV
* Items added seamlessly into the cart
* Pricing varies based on size

**Status:** ✔ Fully implemented

---

### **Add Main Side**

**Requirement:**

* Select type of main side

**Implementation:**

* Fully supported via CSV-loaded side items (Appetizers)

**Status:** ✔ Fully implemented

---

### **Checkout**

**Requirement:**

* Display order details
* Display price
* Confirm → generate receipt
* Cancel → delete order and return home

**Implementation:**

* `ConsoleCheckoutScreen` shows:
  ✔ Customer name
  ✔ Itemized cart with ingredient breakdown
  ✔ Subtotal and total
  ✔ Payment method
  ✔ Notes (optional)
* Confirmation creates:
  ✔ `Transaction`
  ✔ Receipt file using `ReceiptFileManager`
* Cancel returns to home without saving a receipt

**Status:** ✔ Fully implemented
**Enhancement:** Receipt includes full ingredient list and payment summary.

---

## **3. Additional Features Beyond Requirements**

### **✔ Data-Driven Menu (CSV Files)**

Ingredients, recipes, drinks, and sides are loaded from CSV files through data handlers.
This simulates real POS systems and makes adding new items trivial.

### **✔ Transaction System**

Transactions include:

* Billing information
* Th timestamps
* Itemized ingredient summaries
* Automatic total calculation

### **✔ Receipt File Formatting**

Includes:

* Customer name
* Payment method
* Full ingredient breakdown
* Notes/support comments
* Timestamp

### **✔ Clean MVC-Style Architecture**

* Dedicated screen classes
* Controller manages flow
* Model layer is fully encapsulated
* Receipt utilities & data handlers keep IO code isolated

### **✔ Ingredient-Level Pricing**

Every topping has unique cost
Premium toppings add price
Multiple toppings multiply cost

### **✔ Build Your Own Ramen**

Includes:

* Broth
* Protein
* Vegetables
* Premium toppings
* Spice level
* Specials
* Size-based pricing

### **✔ Cart System**

Users can:

* Update quantities
* Remove items
* Review ingredient breakdown

---

## **4. Design Decisions**

### **Separation of Concerns**

Using a dedicated class for each screen avoids monolithic code and enables clean, maintainable expansion.

### **Map-Based Cart**

A `Map<Item, Integer>` ensures:

* Unique items tracked precisely
* Easy quantity management
* Less error-prone than a List-based cart

### **Item Inheritance Structure**

`Item` is the abstract base class for:

* Ramen
* Drink
* Appetizer
* Dessert

Allows:

* Shared methods (`calculatePrice()`)
* Clean type extension
* Uniform processing in cart & receipts

### **Transaction & BillingInfo Pair**

Keeps payment concerns separate from order concerns while still linking everything cleanly.

---

## **5. Challenges & Solutions**

### **Screen Navigation Loops**

Issue: Nested loops caused repeated screens
Fix:

* Return control to controller
* Reset screen flags
* Ensure “Exit” breaks the top-level loop once

### **Dynamic Ingredient Breakdown**

Original cart/transaction code only displayed item names
Fix:

* Added `getIngredientSummary()` to `Item`
* Injected summaries into cart, checkout, and receipts

### **CSV Data Relationship**

Recipes depend on ingredients
Fix:

* Ingredient CSV is loaded first
* Recipes are built using ingredient lookup

---
