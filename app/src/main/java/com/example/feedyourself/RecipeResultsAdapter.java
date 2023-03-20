package com.example.feedyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeResultsAdapter extends RecyclerView.Adapter<RecipeResultsAdapter.ViewHolder> {

    private final Context context;
    private final List<String> recipeData; // Replace String with your actual recipe data model class

    public RecipeResultsAdapter(Context context, List<String> recipeData) { // Replace String with your actual recipe data model class
        this.context = context;
        this.recipeData = recipeData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recipe_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String recipe = recipeData.get(position); // Replace String with your actual recipe data model class
        holder.recipeNameTextView.setText(recipe); // Update this line to set the appropriate data for your view
    }

    @Override
    public int getItemCount() {
        return recipeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
        }
    }
}

