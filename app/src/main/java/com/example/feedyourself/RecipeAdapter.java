package com.example.feedyourself;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.recipeIngredients.setText("Ingredients: " + TextUtils.join(", ", recipe.getIngredients()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeMealType = itemView.findViewById(R.id.recipe_meal_type);
            recipeFlavor = itemView.findViewById(R.id.recipe_flavor);
            recipeIngredients = itemView.findViewById(R.id.recipe_ingredients);
        }
    }

}

