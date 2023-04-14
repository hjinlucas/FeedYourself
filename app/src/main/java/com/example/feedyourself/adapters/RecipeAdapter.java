package com.example.feedyourself.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;
import com.example.feedyourself.main.RecipeDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private List<String> savedRecipeIds;
    private boolean isUserLoggedIn;
    private String userId;


    public RecipeAdapter(Context context, List<Recipe> recipeList, List<String> savedRecipeIds, boolean isUserLoggedIn) {
        this.context = context;
        this.recipeList = recipeList;
        this.savedRecipeIds = savedRecipeIds;
        this.isUserLoggedIn = isUserLoggedIn;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeMealType.setText(recipe.getMealType());
        holder.recipeFlavor.setText(recipe.getFlavor());

        if (recipe.getIngredients() != null) {
            holder.recipeIngredients.setText("Ingredients: " + TextUtils.join(", ", recipe.getIngredients()));
        } else {
            holder.recipeIngredients.setText("Ingredients: N/A");
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("selectedRecipe", recipe);
                context.startActivity(intent);
            }
        });

        if (savedRecipeIds.contains(recipe.getId())) {
            holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_full);
        } else {
            holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_outline);
        }
        holder.saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserLoggedIn) {
                    if (savedRecipeIds.contains(recipe.getId())) {
                        // Remove the recipe from saved recipes
                        removeRecipe(recipe);
                        savedRecipeIds.remove(recipe.getId());
                        holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_outline);
                    } else {
                        // Save the recipe
                        saveRecipe(recipe);
                        savedRecipeIds.add(recipe.getId());
                        holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_full);
                    }
                } else {
                    // Show a toast message for users who are not logged in
                    Toast.makeText(context, "You need to log in to save the recipe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public TextView recipeMealType;
        public TextView recipeFlavor;
        public TextView recipeIngredients;

        public ImageButton saveRecipeButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeMealType = itemView.findViewById(R.id.recipe_meal_type);
            recipeFlavor = itemView.findViewById(R.id.recipe_flavor);
            recipeIngredients = itemView.findViewById(R.id.recipe_ingredients);
            saveRecipeButton = itemView.findViewById(R.id.save_button);

        }
    }

    private void saveRecipe(Recipe recipe) {
        if (isUserLoggedIn) {
            DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("savedRecipes");
            savedRecipesRef.child(recipe.getId()).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void removeRecipe(Recipe recipe) {
        if (isUserLoggedIn) {
            DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("savedRecipes").child(recipe.getId());
            savedRecipesRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Recipe Removed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
