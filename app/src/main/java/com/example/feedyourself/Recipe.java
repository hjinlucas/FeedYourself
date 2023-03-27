package com.example.feedyourself;

import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private String mealType;
    private List<String> ingredients;
    private String flavor;


    public Recipe() {
        // Required empty public constructor
    }

    public Recipe(String id, String name, String mealType, List<String> ingredients, String flavor) {
        this.id = id;
        this.name = name;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.flavor = flavor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMealType() {
        return mealType;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getFlavor() {
        return flavor;
    }


}

