package com.example.feedyourself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView recipeNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeNameTextView = findViewById(R.id.recipeNameTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String recipeName = intent.getStringExtra("RECIPE_NAME");
            recipeNameTextView.setText(recipeName);
        }
    }
}
