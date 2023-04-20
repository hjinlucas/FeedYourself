package com.example.feedyourself.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.RecipeAdapter2;
import com.example.feedyourself.utils.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectRecipeFragment extends Fragment {


    private List<Recipe> recipeList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecipeAdapter2 recipeAdapter2;

    private ImageButton imageButton;

    public SelectRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageButton = view.findViewById(R.id.imageButton1);
        imageButton.setOnClickListener(v -> {
            showBottomSheetDialog();
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_select_recipe, container, false);
        createRecyclerView(view);
        return view;
    }

    private void createRecyclerView(View view){
        Log.d("SelectRecipeFragment", "createRecyclerView");
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");
        SharedViewModel sharedViewModel;
        recyclerView = view.findViewById(R.id.borderlessRecyclerView);
        recipeAdapter2 = new RecipeAdapter2(requireContext(),recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(recipeAdapter2);



        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getIngredients().observe(getViewLifecycleOwner(), ingredientsMap->{
            String mealType = null;
            Log.d("succ","2");
            for (String s : ingredientsMap.keySet()) {
                if(s != null){
                    Log.v("suc","1");
                   mealType = s;
                }
            }
            if(mealType != null){
                Log.v("succ","1");
                List<String> mealTypeIngredients = ingredientsMap.get(mealType);
                recipeList.clear();
                recipesRef.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Recipe recipe = dataSnapshot.getValue(Recipe.class);
//                            if(recipe.getIngredients().retainAll(mealTypeIngredients)){//It means there are duplicates between the two lists
//                                recipeList.add(recipe);
//
//                            }
                            Log.v(recipe.getName(),"1");
                            recipeList.add(recipe);
                        }
                        recipeAdapter2.notifyDataSetChanged();
                        Log.d("SelectRecipeFragment", "Recipes count: " + recipeList.size());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //After this, we will get the required recipes from the database





    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_content, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

}