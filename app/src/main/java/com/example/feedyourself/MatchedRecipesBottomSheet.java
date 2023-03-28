package com.example.feedyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;

public class MatchedRecipesBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_matched_recipes, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipes);
        recyclerView.setAdapter(recipeAdapter);

        return view;
    }

    public void setRecipes(List<Recipe> recipes) {
        if (this.recipes == null) {
            this.recipes = new ArrayList<>();
        }
        this.recipes.clear();
        this.recipes.addAll(recipes);
    }

}

