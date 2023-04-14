package com.example.feedyourself.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

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



public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    private List<String> savedRecipeIds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        savedRecipeIds = new ArrayList<>();

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeList, savedRecipeIds);
        recyclerView.setAdapter(recipeAdapter);

        // Search and display recipes
        searchRecipes("");

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecipes(newText);
                return false;
            }
        });


        return view;
    }

    private void searchRecipes(String query) {
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");
        DatabaseReference savedRecipesRef = FirebaseDatabase.getInstance().getReference("savedRecipes");

        savedRecipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                savedRecipeIds.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String savedRecipeId = snapshot.getKey();
                    savedRecipeIds.add(savedRecipeId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // errors
            }
        });

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                String lowerCaseQuery = query.toLowerCase();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe.getName().toLowerCase().contains(lowerCaseQuery)) {
                        recipeList.add(recipe);
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

