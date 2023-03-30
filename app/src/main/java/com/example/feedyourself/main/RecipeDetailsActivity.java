package com.example.feedyourself.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.feedyourself.R;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView recipeNameTextView;
    private TextView recipeMealTypeTextView;
    private TextView recipeFlavorTextView;
    private TextView recipeIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeNameTextView = findViewById(R.id.recipeNameTextView);
//        recipeMealTypeTextView = findViewById(R.id.recipeMealTypeTextView);
//        recipeFlavorTextView = findViewById(R.id.recipeFlavorTextView);
//        recipeIngredientsTextView = findViewById(R.id.recipeIngredientsTextView);

        Intent intent = getIntent();
//        if (intent != null) {
//            Recipe recipe = intent.getParcelableExtra("selectedRecipe");
//            recipeNameTextView.setText(recipe.getName());
//            recipeMealTypeTextView.setText(recipe.getMealType());
//            recipeFlavorTextView.setText(recipe.getFlavor());
//            recipeIngredientsTextView.setText("Ingredients: " + TextUtils.join(", ", recipe.getIngredients()));
//        }
    }
}
