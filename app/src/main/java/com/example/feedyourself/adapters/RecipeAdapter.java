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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
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

        if (recipe.isSaved()) {
            holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_full);
        } else {
            holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_outline);
        }
        holder.saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe.isSaved()) {
                    // Remove the recipe from saved recipes
                    removeRecipe(recipe);
                    recipe.setSaved(false);
                    holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_outline);
                } else {
                    // Save the recipe
                    saveRecipe(recipe);
                    recipe.setSaved(true);
                    holder.saveRecipeButton.setImageResource(R.drawable.ic_heart_full);
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
        Log.d("RecipeAdapter", "Saving recipe: " + recipe.toString());

        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes");
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

    private void removeRecipe(Recipe recipe) {
        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes").child(recipe.getId());
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

