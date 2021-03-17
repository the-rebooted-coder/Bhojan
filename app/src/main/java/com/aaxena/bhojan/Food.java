package com.aaxena.bhojan;

public class Food {
    String food;
    String description;
    String suggestions;
    String key;
    String imageUrl;

    public Food() {

    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }
}
