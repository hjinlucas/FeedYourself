package com.example.feedyourself.main;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;
import com.example.feedyourself.Recipe;
import com.example.feedyourself.RecipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        Log.d("Adapter", "onBindViewHolder: " + position);
//        holder.recipeImage.setImageResource(recipe.getImgId());
        holder.recipeName.setText(recipe.getName());




    }


    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: " + recipeList.size());
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;


//        public ImageView recipeImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            recipeImage = itemView.findViewById(R.id.select_recipe_img);
            recipeName = itemView.findViewById(R.id.select_recipe_name);

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



}

