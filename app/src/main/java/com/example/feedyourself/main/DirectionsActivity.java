package com.example.feedyourself.main;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.Review;
import com.example.feedyourself.adapters.ReviewAdapter;
import com.example.feedyourself.databinding.ActivityDirectionsBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DirectionsActivity extends AppCompatActivity {
    private ActivityDirectionsBinding binding;
    private Recipe recipe;
    private ReviewAdapter reviewAdapter;

    private List<Review> reviewList = new ArrayList<>();
    private DatabaseReference reviewRef;

    private List<String> ingredients = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        binding.recipeDirectionImage.setImageResource(recipe.getImgId());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset <= scrollRange / 2) { // Adjust the value to show the title sooner or later
                    binding.collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    binding.collapsingToolbarLayout.setTitleEnabled(true);
                    isShow = true;
                } else if (isShow) {
                    binding.collapsingToolbarLayout.setTitleEnabled(false);
                    isShow = false;
                }
            }
        });
        binding.recipeNameDirections.setText(recipe.getName());
        binding.commentIcon.setOnClickListener(v -> {
            leaveComments();
        });
        displayDirections();
        displayIngredients();
        displayRecipeInfo();
        createRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dir_act_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void leaveComments(){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("RecipeComment", recipe);
        startActivity(intent);
    }
    private void createRecyclerView(){
        reviewRef= FirebaseDatabase.getInstance().getReference("Reviews");
        reviewAdapter = new ReviewAdapter(this, reviewList);
        binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.commentsRecyclerView.setAdapter(reviewAdapter);

        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
                    if(review.getRecipeName().equals(recipe.getName())){
                        reviewList.add(review);
                    }

                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displayDirections() {
        String directionsString = recipe.getDirections();
        if (directionsString != null && !directionsString.isEmpty()) {
            String[] directionsArray = directionsString.split("#");
            LinearLayout directionsContainer = binding.directionsContainer;

            for (int i = 0; i < directionsArray.length; i++) {
                TextView directionTextView = new TextView(this);
                directionTextView.setText((i + 1) + ". " + directionsArray[i]);
                directionTextView.setTextSize(16);
                directionTextView.setPadding(0, 0, 0, 8);
                directionsContainer.addView(directionTextView);
            }
        }
    }
    private void displayIngredients() {
        List<String> ingredientsList = recipe.getIngredients();
        if (ingredientsList != null && !ingredientsList.isEmpty()) {
            LinearLayout ingredientsContainer = binding.ingredientsContainer;

            for (String ingredient : ingredientsList) {
                TextView ingredientTextView = new TextView(this);
                ingredientTextView.setText("\u2022 " + ingredient); // Add a bullet point before each ingredient
                ingredientTextView.setTextSize(16);
                ingredientTextView.setPadding(0, 0, 0, 8);
                ingredientsContainer.addView(ingredientTextView);
            }
        }
    }
    private void displayRecipeInfo() {
        List<String> ingredientsList = recipe.getIngredients();
        int numberOfIngredients = (ingredientsList != null) ? ingredientsList.size() : 0;
        Integer calories = recipe.getCalories();
        Integer cookTime = recipe.getTime();

        String recipeInfoText = String.format(Locale.getDefault(),
                "Ingredients: %d  |  Calories: %d kcal  |  Cook time: %d min",
                numberOfIngredients,
                calories,
                cookTime);
        binding.recipeInfoDirections.setText(recipeInfoText);
    }



}