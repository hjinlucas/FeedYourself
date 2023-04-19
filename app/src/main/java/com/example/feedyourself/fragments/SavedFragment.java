package com.example.feedyourself.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private List<Recipe> recipeList;
    private List<String> savedRecipeIds;
    private String userId;
    private boolean isUserLoggedIn;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.saved_recipes_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        savedRecipeIds = new ArrayList<>();

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeList, savedRecipeIds, isUserLoggedIn);
        recyclerView.setAdapter(recipeAdapter);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        isUserLoggedIn = mAuth.getCurrentUser() != null;

        if (isUserLoggedIn) {
            userId = mAuth.getCurrentUser().getUid();
        }

        recipeAdapter = new RecipeAdapter(getActivity(), recipeList, savedRecipeIds, isUserLoggedIn);
        recyclerView.setAdapter(recipeAdapter);

        if (isUserLoggedIn) {
            fetchSavedRecipes();
        }

        return view;
    }

    private void fetchSavedRecipes() {
        Log.d("SavedFragment", "fetchSavedRecipes called");

        if (isUserLoggedIn) {
            DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("savedRecipes");
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

                            // Add a log statement to print the fetched recipe data
                            Log.d("SavedFragment", "Fetched saved recipe: " + savedRecipe.toString());
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


}

