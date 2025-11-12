package com.ichiraku.persistence;

import com.ichiraku.model.*;

import java.util.List;

public class DataManager {
    // *** Fields ***
    private List<Item> items;
    private List<Ingredient> ingredients;
    private List<Ramen> recipes;
    private TransactionLog transactionLog;

    // Path constants
    private static final String INGREDIENT_CSV_PATH = "resources/ichiraku_ingredients.csv";
    private static final String RECIPE_CSV_PATH = "resources/ichiraku_recipes.csv";

    private CsvIngredientDataHandler ingredientHandler;
    private CsvRecipeDataHandler recipeHandler;
    //private ReceiptFileHandler receiptHandler;
    //private CsvTransactionLogHandler transactionHandler; // optional, later

    public DataManager() {
        // Load data
        //this.ingredients = loadIngredients();
        //this.items = loadItems();
        //this.recipes = loadRecipes();
        //this.transactionLog = loadTransactionLog();
        // Initialize the handler with the file path

        ingredientHandler = new CsvIngredientDataHandler(INGREDIENT_CSV_PATH);
        ingredients = ingredientHandler.load();

        recipeHandler = new CsvRecipeDataHandler(RECIPE_CSV_PATH, ingredients);
        recipes = recipeHandler.load();
    }

    private List<Ingredient> loadIngredients() {
        // Let the handler do the CSV parsing
        return ingredientHandler.load();
    }

    // You can expose save methods through DataManager too
    public void saveIngredient(Ingredient ingredient) {
        ingredientHandler.save(ingredient);
    }

    public void saveAllIngredients(List<Ingredient> ingredients) {
        ingredientHandler.saveAll(ingredients);
    }

    // ... other load/save methods for items, recipes, transactionLog
    private List<Ramen> loadRecipes() {
        return recipeHandler.load();
    }

    public void saveRecipe(Ramen recipe) {
        recipeHandler.save(recipe);
    }

    public void saveAllRecipes(List<Ramen> recipes) {
        recipeHandler.saveAll(recipes);
    }

    public void loadAllData() {
        ingredients = ingredientHandler.load();
        recipes = recipeHandler.load();
        // items = combine ingredients + recipes if needed
    }

    public List<Ingredient> getAllIngredients() {
        return ingredients;
    }

    public List<Ramen> getAllRecipes() {
        return recipes;
    }

    public List<Item> getAllItems() {
        return items;
    }


//    public void saveTransaction(Transaction transaction) {
//        // append to transaction log
//        transactionHandler.save(transaction);
//
//        // optionally generate receipt
//        receiptHandler.generateReceipt(transaction);
//    }

}

