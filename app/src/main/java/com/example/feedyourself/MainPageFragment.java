package com.example.feedyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                R.drawable.ic_launcher_background,
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
        CardView brunchCard = view.findViewById(R.id.brunchCard);
        CardView lunchCard = view.findViewById(R.id.lunchCard);
        CardView dinnerCard = view.findViewById(R.id.dinnerCard);

        View.OnClickListener mealTypeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform any action you want when a meal type is selected

                // Show the confirmation button when a meal type is selected
                setupMealTypeCardClickListeners();
                confirmationButton.setVisibility(View.VISIBLE);
            }
        };

        breakfastCard.setOnClickListener(mealTypeClickListener);
        brunchCard.setOnClickListener(mealTypeClickListener);
        lunchCard.setOnClickListener(mealTypeClickListener);
        dinnerCard.setOnClickListener(mealTypeClickListener);



        //==================================================================================================
        //================================== Confirmation btn ==============================================
        //==================================================================================================
        confirmationButton = view.findViewById(R.id.confirmationButton);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event for the confirmation button
                // For example, navigate to the next page with the selected meal type
            }
        });

    }

    private void setupMealTypeCardClickListeners() {
//        CardView breakfastCard = findViewById(R.id.breakfastCard);
//        CardView brunchCard = findViewById(R.id.brunchCard);
//        CardView lunchCard = findViewById(R.id.lunchCard);
//        CardView dinnerCard = findViewById(R.id.dinnerCard);

        breakfastCard.setOnClickListener(view -> toggleMealTypeDescriptionVisibility(breakfastCard));
        brunchCard.setOnClickListener(view -> toggleMealTypeDescriptionVisibility(brunchCard));
        lunchCard.setOnClickListener(view -> toggleMealTypeDescriptionVisibility(lunchCard));
        dinnerCard.setOnClickListener(view -> toggleMealTypeDescriptionVisibility(dinnerCard));
    }

    private void toggleMealTypeDescriptionVisibility(CardView mealTypeCard) {
        TextView ingredientsTitle = mealTypeCard.findViewById(R.id.ingredientsTitle);
        TextView flavorTitle = mealTypeCard.findViewById(R.id.flavorTitle);
        LinearLayout ingredientsLayout = mealTypeCard.findViewById(R.id.ingredientsLayout);
        LinearLayout flavorLayout = mealTypeCard.findViewById(R.id.flavorLayout);

        if (ingredientsTitle.getVisibility() == View.GONE) {
            ingredientsTitle.setVisibility(View.VISIBLE);
            flavorTitle.setVisibility(View.VISIBLE);
            ingredientsLayout.setVisibility(View.VISIBLE);
            flavorLayout.setVisibility(View.VISIBLE);
        } else {
            ingredientsTitle.setVisibility(View.GONE);
            flavorTitle.setVisibility(View.GONE);
            ingredientsLayout.setVisibility(View.GONE);
            flavorLayout.setVisibility(View.GONE);
        }
    }



}
