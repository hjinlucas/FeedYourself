package com.example.feedyourself.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, List<String>>> ingredients = new MutableLiveData<>(new HashMap<>());

    public MutableLiveData<HashMap<String, List<String>>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String mealType, List<String> ingredientList) {
        HashMap<String, List<String>> currentIngredients = ingredients.getValue();
        if (currentIngredients != null) {
            currentIngredients.put(mealType, ingredientList);
            ingredients.setValue(currentIngredients);
        }
    }

    public void clearData() {
        ingredients.setValue(new HashMap<>());
    }

}
