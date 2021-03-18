package com.aaxena.bhojan;

public class Food {
    String food;
    String description;
    String suggestions;
    String key;
    String imageUrl;
    String latitude;
    String longitude;

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

    public Food(String food, String description, String suggestions, String latitude, String longitude) {
        this.food = food;
        this.description = description;
        this.suggestions = suggestions;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
