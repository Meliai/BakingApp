package com.rudainc.bakingapp.models;

public class Ingredient {
    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredient(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
