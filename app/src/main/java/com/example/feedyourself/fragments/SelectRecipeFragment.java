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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Collections;
import java.util.List;

public class SelectRecipeFragment extends Fragment {


    private List<Recipe> recipeList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecipeAdapter2 recipeAdapter2;

    private ImageButton imageButton;
//    private RadioGroup sortingFilterGroup;
    private RadioButton sortingFilterCalories;
    private RadioButton sortingFilterCookingTime;

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
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");
        SharedViewModel sharedViewModel;
        recyclerView = view.findViewById(R.id.borderlessRecyclerView);
        recipeAdapter2 = new RecipeAdapter2(requireContext(),recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(recipeAdapter2);



        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getIngredients().observe(getViewLifecycleOwner(), ingredientsMap->{
            String mealType = null;

            for (String s : ingredientsMap.keySet()) {
                if(s != null){

                   mealType = s;
                }
            }
            if(mealType != null){
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
                            recipeList.add(recipe);
                        }
                        recipeAdapter2.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_content, null);

        CheckBox sortingFilterCalories = bottomSheetView.findViewById(R.id.sorting_filter_calories);
        CheckBox sortingFilterCookingTime = bottomSheetView.findViewById(R.id.sorting_filter_cooking_time);
        Button saveSortingButton = bottomSheetView.findViewById(R.id.save_sorting_button);

        CompoundButton.OnCheckedChangeListener checkBoxListener = (buttonView, isChecked) -> {
            if (isChecked) {
                if (buttonView.getId() == R.id.sorting_filter_calories) {
                    sortingFilterCookingTime.setChecked(false);
                } else if (buttonView.getId() == R.id.sorting_filter_cooking_time) {
                    sortingFilterCalories.setChecked(false);
                }
            }
        };

        sortingFilterCalories.setOnCheckedChangeListener(checkBoxListener);
        sortingFilterCookingTime.setOnCheckedChangeListener(checkBoxListener);

        saveSortingButton.setOnClickListener(v -> {
            if (sortingFilterCalories.isChecked()) {
                Collections.sort(recipeList, (r1, r2) -> r1.getCalories().compareTo(r2.getCalories()));
            } else if (sortingFilterCookingTime.isChecked()) {
                Collections.sort(recipeList, (r1, r2) -> r1.getTime().compareTo(r2.getTime()));
            }
            recipeAdapter2.notifyDataSetChanged();
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }




}