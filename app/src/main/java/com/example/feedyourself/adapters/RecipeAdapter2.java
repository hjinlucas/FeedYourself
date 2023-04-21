package com.example.feedyourself.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;
import com.example.feedyourself.main.DirectionsActivity;

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

        return new RecipeAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter2.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.recipeImage.setImageResource(recipe.getImgId());
        holder.recipeName.setText(recipe.getName());
        holder.recipeCalorie.setText(String.valueOf(recipe.getCalories()) + "kcal");
        holder.recipeTime.setText(String.valueOf(recipe.getTime()) + "mins");





    }


    @Override
    public int getItemCount() {

        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public ImageView recipeImage;
        public TextView recipeCalorie;
        public TextView recipeTime;

//

//        public ImageView recipeImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            recipeImage = itemView.findViewById(R.id.select_recipe_img);
            recipeName = itemView.findViewById(R.id.select_recipe_name);
            recipeImage = itemView.findViewById(R.id.select_recipe_background_image);
            recipeCalorie = itemView.findViewById(R.id.select_recipe_calorie);
            recipeTime = itemView.findViewById(R.id.select_recipe_time);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Recipe clickedRecipe = recipeList.get(position);
                Intent intent = new Intent(context, DirectionsActivity.class);
                intent.putExtra("recipe",clickedRecipe);
                context.startActivity(intent);
            });


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
    public void updateData(List<Recipe> newData) {
        this.recipeList = newData;
        notifyDataSetChanged();
    }


}
