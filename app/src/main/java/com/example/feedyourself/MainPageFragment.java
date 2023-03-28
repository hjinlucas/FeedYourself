package com.example.feedyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;


public class MainPageFragment extends Fragment {

    private TextView forYouTextView;
    private TextView exploreTextView;
    private TextView premiumTextView;

    private RecyclerView horizontalRecyclerView;
    private CardView breakfastCard;
    private CardView brunchCard;
    private CardView lunchCard;
    private CardView dinnerCard;
    private FloatingActionButton confirmationButton;

    private List<CheckBox> ingredientCheckBoxes;
    private List<CheckBox> flavorCheckBoxes;

    private String selectedMealType;

    private int expandedPosition = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox checkBox1 = view.findViewById(R.id.checkBox1);
        CheckBox checkBox2 = view.findViewById(R.id.checkBox2);
        CheckBox checkBox3 = view.findViewById(R.id.checkBox3);
        CheckBox checkBox4 = view.findViewById(R.id.checkBox4);
        CheckBox checkBox5 = view.findViewById(R.id.checkBox5);
        CheckBox checkBox6 = view.findViewById(R.id.checkBox6);
        CheckBox checkBox7 = view.findViewById(R.id.checkBox7);



        // Initialize views and set up click listeners for the main page content here
        //==================================================================================================
        //============= OnClickListener for small menu in main page ========================================
        //==================================================================================================
        forYouTextView = view.findViewById(R.id.forYouTextView);
        exploreTextView = view.findViewById(R.id.exploreTextView);
        premiumTextView = view.findViewById(R.id.premiumTextView);

        breakfastCard = view.findViewById(R.id.breakfastCard);
        brunchCard = view.findViewById(R.id.brunchCard);
        lunchCard = view.findViewById(R.id.lunchCard);
        dinnerCard = view.findViewById(R.id.dinnerCard);

        forYouTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event for ForYou TextView
            }
        });

        exploreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Handle click event for Explore TextView
            }
        });

        premiumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event for Premium TextView
            }
        });


        //==================================================================================================
        //============= OnClickListener for cards that display meal recommendations =======================
        //==================================================================================================
        RecyclerView horizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);

        List<Integer> imageIds = Arrays.asList(
                R.drawable.meal_beef_tomato,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background
                //just for test now, replace image later
        );

        ImageAdapter imageAdapter = new ImageAdapter(requireContext(), imageIds);
        horizontalRecyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle click event for the ImageView in the CardView
                // You can get the selected image ID with imageIds.get(position)
            }
        });


        //==================================================================================================
        //============= OnClickListener for cards that display image of breakfast, brunch, etc =============
        //==================================================================================================
        // Add this code inside the onCreate method, after initializing the horizontal RecyclerView

        CardView breakfastCard = view.findViewById(R.id.breakfastCard);
        TextView breakfastTitle = breakfastCard.findViewById(R.id.mealTypeTitle);
        breakfastTitle.setText("Breakfast");
        CardView brunchCard = view.findViewById(R.id.brunchCard);
        TextView brunchTitle = brunchCard.findViewById(R.id.mealTypeTitle);
        brunchTitle.setText("Brunch");
        CardView lunchCard = view.findViewById(R.id.lunchCard);
        TextView lunchTitle = lunchCard.findViewById(R.id.mealTypeTitle);
        lunchTitle.setText("Lunch");
        CardView dinnerCard = view.findViewById(R.id.dinnerCard);
        TextView dinnerTitle = dinnerCard.findViewById(R.id.mealTypeTitle);
        dinnerTitle.setText("Dinner");

        // Expand the first card view by default
        expandCardView(breakfastCard);


        setMealTypeCardViewClickListener(breakfastCard);
        setMealTypeCardViewClickListener(brunchCard);
        setMealTypeCardViewClickListener(lunchCard);
        setMealTypeCardViewClickListener(dinnerCard);

//        View.OnClickListener mealTypeClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform any action you want when a meal type is selected
//
//                // Show the confirmation button when a meal type is selected
//                confirmationButton.setVisibility(View.VISIBLE);
//            }
//        };
//
//        breakfastCard.setOnClickListener(mealTypeClickListener);
//        brunchCard.setOnClickListener(mealTypeClickListener);
//        lunchCard.setOnClickListener(mealTypeClickListener);
//        dinnerCard.setOnClickListener(mealTypeClickListener);



        //==================================================================================================
        //================================== Confirmation btn ==============================================
        //==================================================================================================
        confirmationButton = view.findViewById(R.id.confirmationButton);



        ingredientCheckBoxes = new ArrayList<>();
        flavorCheckBoxes = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            int ingredientId = getResources().getIdentifier("checkBox" + i, "id", getActivity().getPackageName());
            ingredientCheckBoxes.add(view.findViewById(ingredientId));
        }

        for (int i = 1; i <= 3; i++) {
            int flavorId = getResources().getIdentifier("flavorCheckBox" + i, "id", getActivity().getPackageName());
            flavorCheckBoxes.add(view.findViewById(flavorId));
        }

        View.OnClickListener mealTypeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.breakfastCard:
                        selectedMealType = "Breakfast";
                        break;
                    case R.id.brunchCard:
                        selectedMealType = "Brunch";
                        break;
                    case R.id.lunchCard:
                        selectedMealType = "Lunch";
                        break;
                    case R.id.dinnerCard:
                        selectedMealType = "Dinner";
                        break;
                }
            }
        };

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Recipe> filteredRecipes = filterRecipes();

                // Show the filtered recipes in the bottom sheet
                MatchedRecipesBottomSheet bottomSheet = new MatchedRecipesBottomSheet();
                bottomSheet.setRecipes(filteredRecipes);
                bottomSheet.show(getParentFragmentManager(), "matchedRecipesBottomSheet");

            }
        });

    }

    private List<Recipe> filterRecipes() {
        // Get the selected options (meal type, ingredients, and flavors) from the checkboxes
        List<String> selectedIngredients = getSelectedIngredients();
        String selectedFlavors = getSelectedFlavors();

        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");
        List<Recipe> filteredRecipes = new ArrayList<>();

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);

                    // Check if the recipe matches the selected options
                    if (recipe.getMealType().equalsIgnoreCase(getSelectedMealType())
                            && recipe.getIngredients().containsAll(selectedIngredients)
                            && recipe.getFlavor().equalsIgnoreCase(getSelectedFlavors())) {
                        filteredRecipes.add(recipe);
                    }
                }

                // Create a MatchedRecipesBottomSheet instance and pass the filtered recipes to it
                MatchedRecipesBottomSheet bottomSheet = new MatchedRecipesBottomSheet();
                bottomSheet.setRecipes(filteredRecipes);
                bottomSheet.show(getChildFragmentManager(), "matchedRecipesBottomSheet");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
        return filteredRecipes;
    }

    private String getSelectedMealType() {
        switch (expandedPosition) {
            case 0:
                return "Breakfast";
            case 1:
                return "Brunch";
            case 2:
                return "Lunch";
            case 3:
                return "Dinner";
            default:
                return ""; // Return an empty string if no meal type is selected
        }
    }


    private List<String> getSelectedIngredients() {
        List<String> selectedIngredients = new ArrayList<>();


        for (CheckBox checkBox : ingredientCheckBoxes) {
            if (checkBox.isChecked()) {
                selectedIngredients.add(checkBox.getText().toString());
            }
        }

        return selectedIngredients;

    }

    private String getSelectedFlavors() {

        CheckBox spicyCheckBox = getView().findViewById(R.id.checkBox5);
        CheckBox sweetCheckBox = getView().findViewById(R.id.checkBox6);
        CheckBox saltyCheckBox = getView().findViewById(R.id.checkBox7);


        if (sweetCheckBox.isChecked()) {
            return "Sweet";
        } else if (saltyCheckBox.isChecked()) {
            return "Salty";
        } else if (spicyCheckBox.isChecked()) {
            return "Spicy";
        }

        return "";


    }


    private void setMealTypeCardViewClickListener(CardView cardView) {
        cardView.setOnClickListener(v -> {
            int clickedPosition = ((ViewGroup) cardView.getParent()).indexOfChild(cardView);
            CardView prevCard = (CardView) ((ViewGroup) cardView.getParent()).getChildAt(expandedPosition);
            if (clickedPosition != expandedPosition) {
                expandCardView(cardView);
                collapseCardView(prevCard);
                expandedPosition = clickedPosition;
            }
        });
    }


    private void expandCardView(CardView cardView) {
        TransitionManager.beginDelayedTransition((ViewGroup) cardView.getParent(), new AutoTransition());

        cardView.findViewById(R.id.ingredientsTitle).setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.ingredientsLayout).setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.flavorTitle).setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.flavorLayout).setVisibility(View.VISIBLE);
    }

    private void collapseCardView(CardView cardView) {
        TransitionManager.beginDelayedTransition((ViewGroup) cardView.getParent(), new AutoTransition());

        cardView.findViewById(R.id.ingredientsTitle).setVisibility(View.GONE);
        cardView.findViewById(R.id.ingredientsLayout).setVisibility(View.GONE);
        cardView.findViewById(R.id.flavorTitle).setVisibility(View.GONE);
        cardView.findViewById(R.id.flavorLayout).setVisibility(View.GONE);
    }




}
