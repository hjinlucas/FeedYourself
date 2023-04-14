package com.example.feedyourself.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.RecipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private List<Recipe> recipeList;
    private List<String> savedRecipeIds;


    public SavedFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        savedRecipeIds = new ArrayList<>();

        recyclerView = view.findViewById(R.id.saved_recipes_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeList, savedRecipeIds);
        recyclerView.setAdapter(recipeAdapter);

        fetchSavedRecipes();


        return view;
    }

    private void fetchSavedRecipes() {
        Log.d("SavedFragment", "fetchSavedRecipes called");
        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes");
        savedRecipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                savedRecipeIds.clear();

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe savedRecipe = recipeSnapshot.getValue(Recipe.class);
                    if (savedRecipe != null) {
                        savedRecipeIds.add(savedRecipe.getId());
                        recipeList.add(savedRecipe);
                    }
                }

                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // errors
            }
        });
    }

}
