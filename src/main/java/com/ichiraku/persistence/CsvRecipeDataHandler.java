package com.ichiraku.persistence;

import com.ichiraku.model.*;
import com.ichiraku.enums.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvRecipeDataHandler {

    private final String filePath;
    private final Map<String, Ingredient> ingredientMap;

    public CsvRecipeDataHandler(String filePath, List<Ingredient> ingredients) {
        this.filePath = filePath;
        this.ingredientMap = new HashMap<>();
        for (Ingredient ing : ingredients) {
            ingredientMap.put(ing.getId(), ing);
        }
    }


    public List<Ramen> load() {
        List<Ramen> recipes = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            boolean firstLine = true; // skip header
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                Ramen recipe = parseRecipe(line);
                if (recipe != null) recipes.add(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public void save(Ramen recipe) {
        String csvLine = convertRecipeToCSV(recipe);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(List<Ramen> recipes) {
        List<String> csvLines = new ArrayList<>();
        csvLines.add("id|name|description|bowlSizeId|brothId|noodleId|spiceId|extraIngredientIds");
        for (Ramen recipe : recipes) {
            csvLines.add(convertRecipeToCSV(recipe));
        }
        try {
            Files.write(Paths.get(filePath), csvLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Ramen parseRecipe(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 8) return null;
        try {
            String id = parts[0];
            String name = parts[1];
            String description = parts[2];
            String bowlSizeId = parts[3];
            String brothId = parts[4];
            String noodleId = parts[5];
            String spiceId = parts[6];
            String extras = parts[7];

            Ingredient bowlSize = ingredientMap.get(bowlSizeId);
            Ingredient broth = ingredientMap.get(brothId);
            Ingredient noodle = ingredientMap.get(noodleId);
            Ingredient spice = ingredientMap.get(spiceId);

            Ramen.Builder builder = new Ramen.Builder(id, name, description)
                    .setBowlSize(bowlSize)
                    .setBroth(broth)
                    .setNoodle(noodle)
                    .setSpice(spice);

            if (!extras.isBlank()) {
                String[] extraIds = extras.split(",");
                for (String extraId : extraIds) {
                    Ingredient extra = ingredientMap.get(extraId.trim());
                    if (extra != null) builder.addIngredient(extra);
                }
            }

            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertRecipeToCSV(Ramen recipe) {
        StringBuilder extras = new StringBuilder();
        for (Ingredient ing : recipe.getCustomIngredients()) {
            if (extras.length() > 0) extras.append(",");
            extras.append(ing.getId());
        }
        return String.join("|",
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getBowlSize().getId(),
                recipe.getBaseBroth().getId(),
                recipe.getNoodleType().getId(),
                recipe.getSpiceLevel().getId(),
                extras.toString()
        );
    }
}
