package com.aaxena.bhojan;

public class Food {
    String food;
    String description;
    String sugs;

    public Food(String food, String description, String sugs) {
        this.food = food;
        this.description = description;
        this.sugs = sugs;
    }

    public String getFood() {
        return food;
    }

    public String getDescription() {
        return description;
    }

    public String getSugs() {
        return sugs;
    }
}
