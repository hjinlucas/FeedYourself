package com.example.feedyourself;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable{
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
}

