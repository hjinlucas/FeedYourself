package com.example.feedyourself.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.RecipeAdapter2;
import com.example.feedyourself.adapters.RecipeDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeDetailAdapter adapter;
    private ImageView recipeImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recyclerView = findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeImageView = findViewById(R.id.recipe_image_view);


        Intent intent = getIntent();
        Recipe selectedRecipe = intent.getParcelableExtra("selectedRecipe");

        List<Pair<String, String>> details = new ArrayList<>();
        details.add(new Pair<>("Name", selectedRecipe.getName()));
        details.add(new Pair<>("Meal Type", selectedRecipe.getMealType()));
        details.add(new Pair<>("Flavor", selectedRecipe.getFlavor()));
        details.add(new Pair<>("Ingredients", String.join(", ", selectedRecipe.getIngredients())));
        details.add(new Pair<>("Calories", String.valueOf(selectedRecipe.getCalories())));
        details.add(new Pair<>("Time", String.valueOf(selectedRecipe.getTime())));
        details.add(new Pair<>("Directions", selectedRecipe.getDirections()));
        recipeImageView.setImageResource(selectedRecipe.getImgId());


        adapter = new RecipeDetailAdapter(details);
        recyclerView.setAdapter(adapter);
    }
}
