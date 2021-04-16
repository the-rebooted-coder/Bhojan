package com.aaxena.bhojan;

public class Food {
    private String food;
    private String description;
    private String suggestions;
    private String key;
    private String imageUrl;
    private String latitude;
    private String longitude;

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
