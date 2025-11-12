# Ichiraku Ramen Shop: Capstone Planning Worksheet

## Step 1: Understand the Problem
**Instructions:** In your own words, summarize what this program needs to do.

- Q: What kind of business is this for?
- A: A ramen restaurant with an online store.
- Q: What are customers/employees supposed to do with the application?
- A: A customer/order taker would use the app to gather/collect specifics about how to make the order, and then create a complete order containing possibly multiple custom-built items.
- Q: What should happen after the customer finishes their order?
- A: A receipt file is generated listing the amount due. The completed transaction containing all items in the order and the total is added to the log of all transactions. The specified ingredient amountInStock variable should be updated. Start back at the beginning mainMenu.

---

## Step 2: Identify Requirements
**Instructions:** Make a list of broad requirements based on the project description.  
Look for the key features the application must support. Use bullet points.

**Main Menu:** 
- Place Order (Take the user to the 'New Order' screen) (Button/Redirect link)
- Order History (Button/Redirect link)
- EXIT

**'New Order' Screen:**
- Users should be able to select items from this screen and when the item is selected a prompt to add it to the order/cart should appear.
- The only exception would be for the Build Your Own Ramen that allows a user to select custom items for how to build the ramen (what should go in it).
- View Cart is for when the user is ready to check their cart and either remove items no longer desired- or to continue to CheckOut. 
- Sections:
  - Appetizers Section (Maybe dropdown)
    - Preset/fixed appetizers
  - Ramen section (Maybe dropdown)
    - Prebuilt Ramen
    - Build Your Own Ramen
  - Drinks Section (Maybe dropdown)
    - Soda (Maybe dropdown)
      - Preset soda options
    - Tea (Maybe dropdown)
      - Preset tea options
    - Water
  - Desserts Section (Maybe dropdown)
    - Preset desserts
  - View Cart (Button/Redirect link)

**'Build Your Own Ramen' Screen**
- This is the screen a user is redirected to when they select 'Build Your Own Ramen'.
- Pick Size section (Maybe dropdown)
- Pick Broth section (Maybe dropdown)
- Pick Noodles section (Maybe dropdown)
- Pick Protein section (Maybe dropdown)
- Pick Toppings section (Maybe dropdown)
- Pick Spice Level section (Maybe dropdown)
- *Possibly more options*
- Add item to cart (Button)

**View Cart**
- Order Section: View Data
  - Items (Button to remove items)
  - Quantity of repeated items (Button to change the quantity of the specified item)
  - SubTotal (Calculated total of the order before tax/maybe before discounts)
- Decision Section:
  - Go Back (Button to go back to the 'New Order' Screen)
  - Confirm Order (Button to confirm the order is complete and move to checkout)

**Checkout Screen**
- Billing Info Section (Form with spots for name, email, phone, etc.)
- Checkout (Submit form button, triggers the persistence logic(e.g. creating the receipt/writing to the transactions file))
  - Once the checkout has been processed- display a message, after a few seconds return to the main menu.

**Order History**
- View All orders (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions)
- View orders by date 
  - Start date Calendar selection 
  - End date Calendar selection
  - Search orders (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions within a specified date range)
- View orders by name (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions matching the provided name)
  - Name text entry field
  - Search orders (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions within a specified date range)
- View orders by price (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions within a specified price range)
    - Start price text entry field
    - End price text entry field
    - Search orders (Button/Redirect link to tigger a new window opening(if possible) with all orders/transactions within a specified price range)
- Search orders by id ()
    - Id text entry field
    - Search orders (Button/Redirect link to tigger a new window opening(if possible) with the matching order(if one exists))
- Go Back (Button/Redirect Link to the MainMenu loop)

**EXIT**
- Display a goodbye message. Terminate the app.

**Tip:** Scan the project text and highlight all the nouns and verbs—this can help identify possible classes and actions.

---

## Step 3: User Flow (What Happens First, Next, Then?)
**Instructions:** Imagine you're a customer or employee using this app from start to finish.  
Write a numbered list of the steps a typical user would take.

**Example (do not copy):**
1. Start the program
2. Choose to place an order
3. Add first sandwich
4. ...
---
**User Flow: My Ramen App Version**
- How to place a new order
1. Start the program
2. Select the 'Place Order' option
3. Select an appetizer from the dropdown (or None if no app is desired)
4. Add the appetizer to the cart if one was selected (Click the 'add to cart' button)
5. Either select a prebuilt ramen or 'Build Your Own Ramen' from the dropdown
6. If prebuilt- add the ramen to your cart (Click the 'add to cart' button)- otherwise continue
7. Select a size from the list of options
8. Select a broth from the list of options
9. Select a noodles from the list of options
10. Select a protein from the list of options
11. Select a spice level from the list of options
12. Submit the form (Click the 'add to cart' button)
13. Return to the 'New Order' Screen/Page (Occurs naturally upon adding ramen to cart)
14. Select a drink from the dropdown (or None if no drink is desired)
15. Add the drink to the cart if one was selected (Click the 'add to cart' button)
16. Select a side from the dropdown (or None if no side is desired)
17. Add the side to the cart if one was selected (Click the 'add to cart' button)
18. Select a dessert from the dropdown (or None if no dessert is desired)
19. Add the dessert to the cart if one was selected (Click the 'add to cart' button)
20. Repeat the Select/Add process for any categories of item where you may want more than one.
21. Select 'View Cart'
22. Remove any unwanted items by clicking the trash icon (or maybe by decrementing the quantity for items with a quantity greater than 1)
23. Go back to the main menu loop to add more items to the cart at any point (just return to the 'View Cart' page for next step)
24. Once the user is content with their order- select the 'Confirm Order' button to move onto the 'Checkout' page
25. Enter Billing Info into the form
26. Go back to the 'View Cart' page to continue editing the cart at any point (just return to the 'Checkout' page for next step)
27. Once the user is content with their order & billing info is all entered- select the 'Checkout' button to finalize/commit the order
28. A new receipt file is generated and the transaction is logged to the Receipts file/database
29. Redirect back to the main menu loop (Until EXIT is selected)
---
- How to View the Order History
1. Start the program
2. Select the 'Order History' option
3. Select a filter/search option (e.g. All, Date, Name, Price, and ID)
4. View the Order History
5. Close the popup window
6. Select another filter/search option or go back to main menu loop

---

## Step 4: Design Test Cases
**Instructions:** Write down at least 5 scenarios where you would want to test your program.

Include:
- A sample action (e.g., customer adds 2 sandwiches, both toasted, one with extra cheese)
- What you expect to happen (e.g., order shows correct total, receipt saves to correct location)
- Remember, we like using actual numbers and doing real calculations

**Note:** These tests will help guide your coding later.

**My Test Cases:**
[T = Test | A = Act | E = Expectation/Assertion]
1.  
- T: Add Single Item to Cart 
- A: User selects "Tonkotsu Ramen" and adds 1 to cart.
- E: Cart size = 1, Item name and price displayed correctly, Subtotal = price of Tonkotsu Ramen
2.  
- T: Add Multiple of Same Item to Cart 
- A: User adds 3 orders of “Matcha Tea.”
- E: Cart displays “Matcha Tea (x3)”, subtotal equals 3 × price of Matcha Tea, and total updates correctly.
3.
- T: Remove Item from Cart
- A: User removes 1 Gyoza from cart.
- E: Gyoza no longer appears in cart list, total price recalculates, and cart item count decreases by 1.
4.
- T: Build Custom Ramen (Builder Pattern Test)
- A: User builds ramen with: Size = Large, Broth = Shoyu, Noodles = Thick, Protein = Pork, Toppings = Egg + Bamboo Shoots, Spice Level = 3.
- E: Ramen object is created successfully via builder; toString() lists all selected options; price is correct based on base + add-ons.
5.
- T: Checkout Process
- A: User confirms an order with 3 total items.
- E: A Transaction object is created; total matches sum of all cart items; a receipt CSV entry is added with transaction ID, date/time, and total; cart clears after checkout.
6.
- T: View Order History (All Orders)
- A: User selects “View All Orders.”
- E: All transactions are displayed in the console, each formatted as:
transactionId | dateTime | items | totalPrice
7.
- T: Attempt Checkout with Empty Cart
- A: User clicks “Checkout” when no items are in cart.
- E: Error message displays (“Cart is empty”), no transaction is created, and user remains on cart screen.
8.
- T: Validate Total Price Precision
- A: User adds multiple items with varying decimal prices (e.g., $8.99 + $3.25 + $1.50).
- E: Total price rounds to two decimal places using RoundingMode.HALF_UP; final total matches expected sum to two decimals.

---

## Step 5: Brainstorm Potential Classes
**Instructions:** Based on what the program needs to do and the nouns you found earlier, list at least 4–6 possible classes you may need.

- You are **NOT** writing code—just brainstorming.
- Once you have a list, check in with your instructor to get feedback.

**My Brainstormed Classes**
- Customer, 
- Order, 
- Item, 
- Ramen, 
- Drink, 
- Dessert, 
- Side, 
- Appetizer, 
- Cart, 
- Transaction, 
- Menu, 
- Receipt, 
- Ingredient, 
- AmountInStock, 
- OrderHistory, 
- Checkout, 
- BillingInfo, 
- Screen

---

## Step 6: Define Responsibilities
**Instructions:** For each class you listed above, write what that class might be responsible for.

Think about:
- What does this class know?
- What does this class do?

| Class Name              | What it knows (data)                                                                       | What it does (behavior/methods)                                                                                                                                                 |
|-------------------------|--------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Customer                | Customer data (name, email, etc.)                                                          | Represents the current customer in the receipt and orderHistory file.                                                                                                           |
| Order                   | Order data (Items data, customer data, etc.)                                               | Holds ordered items until checkout, add passed items to the list of items, remove items from the list.                                                                          |
| Item                    | Item data (item name, price, ingredients, etc.)                                            | Represents a single Item in the cart/order. Can be added/removed to/from the cart.                                                                                              |
| Ramen                   | Ramen data (broth, protein, noodles, etc.)                                                 | Represents a completed ramen item. Can be added/removed to/from the cart. Customizable ingredients.                                                                             |
| Drink                   | Drink data (name, size, price, etc.)                                                       | Represents a completed drink item. Can be added/removed to/from the cart.                                                                                                       |
| Dessert                 | Similar to Item, Ramen, Drink                                                              | Similar to Item, Ramen, Drink                                                                                                                                                   |
| Side                    | Similar to Item, Ramen, Drink                                                              | Similar to Item, Ramen, Drink                                                                                                                                                   |
| Appetizer               | Similar to Item, Ramen, Drink                                                              | Similar to Item, Ramen, Drink                                                                                                                                                   |
| Cart                    | All order data BEFORE checkout (All items, subTotal, etc.)                                 | Represents a collection of the order data BEFORE checkout. Items can be added/removed to/from. Can calculate the subTotal. Passes data into a Transaction object upon checkout. |
| Transaction             | All order data AFTER checkout (All items, salesTex, total, date, time, customerData, etc.) | Represents a completed order/transaction. Is printed to a csv and recorded in a orderHistory file after checkout.                                                               |
| Menu                    | Menus used in the userInterface.                                                           | Contains menus that are displayed to the console in the userInterface.                                                                                                          |
| Receipt                 | Same as Transaction                                                                        | Same as Transaction                                                                                                                                                             |
| Ingredient              | Ingredient data (name, price, etc.)                                                        | Holds ingredient data. Gets added to ramen and possibly other items in future. Contributes to the total price.                                                                  |
| AmountInStock           | Quantity/amount of an ingredient left in the stores inventory.                             | Is considered when allowing a user to pick ingredients for their item. Likely just a property, not a class itself.                                                              |
| OrderHistoryFileManager | The path of the transactions file.                                                         | Reads and Writes to/from the transactions file. Can generate a list of past transactions which contain orders and customer data.                                                |
| CheckoutUserInterface   | The users input. How to use the other classes.                                             | Handles the display the user sees during the checkout process. Directs the user requests/actions to the proper methods.                                                         |
| BillingInfo             | The user info gathered during checkout.                                                    | Likely just properties, not a class itself.                                                                                                                                     |
| Screen                  | The display mechanics.                                                                     | Displays different visuals to the users screen.                                                                                                                                 |


---

## Step 7: Relationships Between Classes
**Instructions:** Now think about how the classes relate to each other.

- Are there classes that contain or use other classes?
  - I didn't specify the class in step 5 but the Controller class(s) would obviously use the builder class and other utility classes.
  - Cart, Order, Transaction, CheckoutUserInterface could or would all potentially have and work Item objects.
- Are there 1-to-many or has-a relationships?
  - Controller is going to possibly have a 1-to-many relationship.
- Inheritance? Interfaces? Abstract classes?
  - Item will be inherited- possibly abstract. Not planning any interfaces.

Write your thoughts or draw lines between classes on paper. You’ll use this in the next step.

## Step 7.5: Decide on Starting Classes
**Instructions:** Now eliminate all the unneeded/unwanted possible classes and pick the ones you want to start with.

---

### ⚙️ **Controller Layer**

1. ConsoleController
2. DataManager

---

### 🖥 **Console UI Layer**

3. ConsoleMainScreen
4. ConsoleOrderScreen
5. ConsoleBuildYourOwnRamenScreen
6. ConsoleCartScreen
7. ConsoleCheckoutScreen
8. ConsoleTransactionLogScreen

---

### 🧩 **Model Layer**

9. Item *(abstract)*
10. Ramen *(extends Item)*
11. Drink *(extends Item)*
12. Appetizer *(extends Item)*
13. Dessert *(extends Item)*
14. Ingredient *(extends Item)*
15. Recipe
16. Cart
17. Transaction *(immutable)*
18. TransactionLog
19. BillingInfo

---

### 🧮 **Persistence / I/O Layer**

20. TransactionLogHandler *(interface)*
21. CsvTransactionLogHandler *(implements TransactionLogHandler)*
22. DatabaseTransactionLogHandler *(implements TransactionLogHandler)*
23. ItemDataHandler *(interface)*
24. CsvItemDataHandler *(implements ItemDataHandler)*
25. DatabaseItemDataHandler *(implements ItemDataHandler)*
26. IngredientDataHandler *(interface)*
27. CsvIngredientDataHandler *(implements IngredientDataHandler)*
28. DatabaseIngredientDataHandler *(implements IngredientDataHandler)*
29. RecipeDataHandler *(interface)*
30. CsvRecipeDataHandler *(implements RecipeDataHandler)*
31. DatabaseRecipeDataHandler *(implements RecipeDataHandler)*
32. ReceiptFileHandler

---

### 🧾 **Enums**

33. ItemCategory
34. IngredientCategory
35. RecipeCategory

### **Added**
36. TransactionStatus (enum)
37. Customer (model)
38. CustomerBuilder (helper)

---

### ⚙️ **Controller Layer**

1. ConsoleController
2. DataManager

---

### 🖥 **Console UI Layer**

3. ConsoleMainScreen
4. ConsoleOrderScreen
5. ConsoleBuildYourOwnRamenScreen
6. ConsoleCartScreen
7. ConsoleCheckoutScreen
8. ConsoleTransactionLogScreen

---

### 🧩 **Model Layer**

9. Item *(abstract)*
10. Ramen *(extends Item)*
11. Drink *(extends Item)*
12. Appetizer *(extends Item)*
13. Dessert *(extends Item)*
14. Ingredient *(extends Item)*
15. Recipe
16. Cart
17. Transaction *(immutable)*
18. TransactionLog
19. BillingInfo

---

### 🧮 **Persistence / I/O Layer**

20. CsvTransactionLogHandler
21. CsvItemDataHandler
22. CsvIngredientDataHandler
23. CsvRecipeDataHandler
24. ReceiptFileHandler

---

### 🧾 **Enums**

25. ItemCategory
26. IngredientCategory
27. RecipeCategory

### **Added**
28. TransactionStatus (enum)
29. Customer (model)
30. CustomerBuilder (helper)


---

## Step 8: Create a UML Diagram (Draft)
**Instructions:** Using your brainstormed classes, draw a UML diagram showing:

- Class names
- Attributes (variables)
- Methods (just the important ones)
- Relationships (e.g., arrows for associations or dependencies)

Link: https://excalidraw.com/#json=wxFdgOqH6BXAX5g8zatmO,LyQCzqxgah_ZqL75ZAXFXQ

Don’t worry about being perfect—this is a draft to help guide your design.  
Present your UML diagram to your instructor for final approval. You may change it while you’re doing your Capstone but at least you have a start.

---

## Step 9: Notes

1. Java Swing for GUI
