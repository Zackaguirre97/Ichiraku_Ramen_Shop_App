package com.ichiraku.persistence;

import com.ichiraku.model.*;
import com.ichiraku.enums.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

public class CsvItemDataHandler {

    private String filePath;

    public CsvItemDataHandler(String filePath) {
        this.filePath = filePath;
    }

    public List<Item> load() {
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            boolean firstLine = true; // skip header
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                Item item = parseItem(line);
                if (item != null) items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void save(Item item) {
        String csvLine = convertItemToCSV(item);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(List<Item> items) {
        List<String> csvLines = new ArrayList<>();
        csvLines.add("id|name|description|basePrice|itemCategory");
        for (Item item : items) {
            csvLines.add(convertItemToCSV(item));
        }
        try {
            Files.write(Paths.get(filePath), csvLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Item parseItem(String line) {
        // id|name|description|basePrice|itemCategory
        String[] parts = line.split("\\|");
        if (parts.length != 5) return null;
        try {
            String id = parts[0];
            String name = parts[1];
            String description = parts[2];
            BigDecimal basePrice = new BigDecimal(parts[3]);
            ItemCategory category = ItemCategory.valueOf(parts[4]);

            switch (category) {
                case DRINK -> { return new Drink(id, name, description, basePrice); }
                case APPETIZER -> { return new Appetizer(id, name, description, basePrice); }
                case DESSERT -> { return new Dessert(id, name, description, basePrice); }
                default -> { return null; } // Ramen not included here
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertItemToCSV(Item item) {
        return String.join("|",
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getBasePrice().toString(),
                item.getItemCategory().name()
        );
    }
}
