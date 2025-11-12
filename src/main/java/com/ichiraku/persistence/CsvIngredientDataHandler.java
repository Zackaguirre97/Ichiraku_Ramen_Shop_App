package com.ichiraku.persistence;

import com.ichiraku.model.Ingredient;
import com.ichiraku.enums.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

public class CsvIngredientDataHandler {

    private String filePath = "src/main/resources/ichiraku_ingredients.csv";

    // --- Constructor ---
    public CsvIngredientDataHandler(String filePath) {
        this.filePath = filePath;
    }

    // --- Load all ingredients from CSV ---
    public List<Ingredient> load() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            boolean firstLine = true; // skip header
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                Ingredient ingredient = parseIngredient(line);
                if (ingredient != null) {
                    ingredients.add(ingredient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    // --- Save a single ingredient to CSV ---
    public void save(Ingredient ingredient) {
        String csvLine = convertIngredientToCSV(ingredient);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Save all ingredients to CSV (overwrite file) ---
    public void saveAll(List<Ingredient> ingredients) {
        List<String> csvLines = new ArrayList<>();
        // Add header
        csvLines.add("id|name|description|basePrice|ingredientCategory");
        for (Ingredient ingredient : ingredients) {
            csvLines.add(convertIngredientToCSV(ingredient));
        }
        try {
            Files.write(Paths.get(filePath), csvLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Helper: parse CSV line into Ingredient ---
    private Ingredient parseIngredient(String line) {
        // Assuming pipe-delimited CSV: id|name|description|basePrice|ingredientCategory
        String[] parts = line.split("\\|");
        if (parts.length != 5) return null;
        try {
            String id = parts[0];
            String name = parts[1];
            String description = parts[2];
            BigDecimal basePrice = new BigDecimal(parts[3]);
            IngredientCategory category = IngredientCategory.valueOf(parts[4]);
            return new Ingredient(id, name, description, basePrice, category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- Helper: convert Ingredient to CSV line ---
    private String convertIngredientToCSV(Ingredient ingredient) {
        return String.join("|",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getDescription(),
                ingredient.getBasePrice().toString(),
                ingredient.getIngredientCategory().name()
        );
    }
}
