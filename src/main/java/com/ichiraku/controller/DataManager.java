package com.ichiraku.controller;

import com.ichiraku.model.*;
import com.ichiraku.persistence.CsvIngredientDataHandler;
import com.ichiraku.persistence.CsvItemDataHandler;
import com.ichiraku.persistence.CsvRecipeDataHandler;

import java.util.List;

public class DataManager {

    // --- Fields ---
    private List<Item> items;
    private List<Ingredient> ingredients;
    private List<Ramen> recipes;
    private List<Appetizer> appetizers;
    private List<Drink> drinks;
    private List<Dessert> desserts;
    private TransactionLog transactionLog;

    // --- File Paths ---
    private static final String INGREDIENT_CSV_PATH = "src/main/resources/ichiraku_ingredients.csv";
    private static final String RECIPE_CSV_PATH = "src/main/resources/ichiraku_recipes.csv";
    private static final String ITEM_CSV_PATH = "src/main/resources/ichiraku_items.csv";

    // --- Handlers ---
    private CsvIngredientDataHandler ingredientHandler;
    private CsvRecipeDataHandler recipeHandler;
    private CsvItemDataHandler itemHandler;
    //private ReceiptFileHandler receiptHandler;
    //private CsvTransactionLogHandler transactionHandler;

    // --- Constructor ---
    public DataManager() {
        loadAllData();
    }

    // --- Central Loader ---
    public void loadAllData() {
        ingredientHandler = new CsvIngredientDataHandler(INGREDIENT_CSV_PATH);
        ingredients = ingredientHandler.load();
        recipeHandler = new CsvRecipeDataHandler(RECIPE_CSV_PATH, ingredients);
        recipes = recipeHandler.load();
        itemHandler = new CsvItemDataHandler(ITEM_CSV_PATH);
        items = itemHandler.load();
        // items = mergeItems(ingredients, recipes); // optional later
    }

    // --- Ingredient Methods ---
    public void saveIngredient(Ingredient ingredient) {
        ingredientHandler.save(ingredient);
    }

    public void saveAllIngredients(List<Ingredient> ingredients) {
        ingredientHandler.saveAll(ingredients);
    }

    // --- Recipe Methods ---
    public void saveRecipe(Ramen recipe) {
        recipeHandler.save(recipe);
    }

    public void saveAllRecipes(List<Ramen> recipes) {
        recipeHandler.saveAll(recipes);
    }

    // --- Getters ---
    public List<Ingredient> getAllIngredients() {
        return ingredients;
    }

    public List<Ramen> getAllRecipes() {
        return recipes;
    }

    public List<Item> getAllItems() {
        return items;
    }

    // --- Future Expansion ---
    // public void saveTransaction(Transaction transaction) {
    //     transactionHandler.save(transaction);
    //     receiptHandler.generateReceipt(transaction);
    // }
}

