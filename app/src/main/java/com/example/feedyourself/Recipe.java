package com.example.feedyourself;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

public class Recipe implements Parcelable{
    private String id;
    private String name;
    private String mealType;
    private List<String> ingredients;
    private String flavor;

    private boolean isSaved;

    private Integer calories;

    private Integer time;

    private String directions;

    private Integer imgId;



    public Recipe() {
        // Required empty public constructor
    }

    public Recipe(String id, String name, String mealType, List<String> ingredients, String flavor, boolean isSaved, Integer calories, Integer time, String directions, Integer imgId) {
        this.id = id;
        this.name = name;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.flavor = flavor;
        this.isSaved= isSaved;
        this.calories = calories;
        this.time = time;
        this.directions = directions;
        this.imgId = imgId;
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

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public Integer getCalories() {
        return calories;
    }

    public Integer getImgId() {
        return imgId;
    }

    public Integer getTime() {
        return time;
    }

    public String getDirections() {
        return directions;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        mealType = in.readString();
        flavor = in.readString();
        ingredients = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write data to the Parcel
        dest.writeString(name);
        dest.writeString(mealType);
        dest.writeString(flavor);
        dest.writeStringList(ingredients);
    }

    public static Recipe fromDataSnapshot(DataSnapshot dataSnapshot) {
        Recipe recipe = dataSnapshot.getValue(Recipe.class);
        if (recipe != null) {
            recipe.setId(dataSnapshot.getKey());
        }
        return recipe;
    }

}

