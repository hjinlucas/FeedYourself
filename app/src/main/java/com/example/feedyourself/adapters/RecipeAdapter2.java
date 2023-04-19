package com.example.feedyourself.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;

import java.util.List;

public class RecipeAdapter2 extends RecyclerView.Adapter<RecipeAdapter2.ViewHolder> {

    private Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter2(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_recipe_item, parent, false);
        Log.d("Adapter", "onCreateViewHolder");
        return new RecipeAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter2.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.recipeMealType.setText(recipe.getMealType());
        holder.recipeFlavor.setText(recipe.getFlavor());
        holder.recipeIngredients.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));

        // Bind other data from the Recipe class to the views as needed
    }





    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: " + recipeList.size());
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public TextView recipeMealType;
        public TextView recipeFlavor;
        public TextView recipeIngredients;


//        public ImageView recipeImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeMealType = itemView.findViewById(R.id.recipe_meal_type);
            recipeFlavor = itemView.findViewById(R.id.recipe_flavor);
            recipeIngredients = itemView.findViewById(R.id.recipe_ingredients);
        }
    }
}

//    private void saveRecipe(Recipe recipe) {
//        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes").child(recipe.getId());
//        savedRecipesRef.child(recipe.getId()).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void removeRecipe(Recipe recipe) {
//        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes").child(recipe.getId());
//        savedRecipesRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(context, "Recipe Removed", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }




