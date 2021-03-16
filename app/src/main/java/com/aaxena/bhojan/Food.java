package com.aaxena.bhojan;

public class Food {
    String food;
    String description;
    String suggestions;

    public Food(String food, String description, String suggestions) {
        this.food = food;
        this.description = description;
        this.suggestions = suggestions;
    }

    public String getFood() {
        return food;
    }

    public String getDescription() {
        return description;
    }

    public String getSuggestions() {
        return suggestions;
    }
}
