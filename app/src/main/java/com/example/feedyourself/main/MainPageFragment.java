package com.example.feedyourself.main;

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

import com.example.feedyourself.ImageAdapter;
import com.example.feedyourself.R;
import com.example.feedyourself.Recipe;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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

    private RecyclerView horizontalRecyclerView;
//    private CardView breakfastCard, brunchCard, lunchCard, dinnerCard;
    private FloatingActionButton confirmationButton;
//    private TextView ingredientsTitle;
    private int expandedPosition = 0;

//    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    private List<Recipe> allRecipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //==================================================================================================
        //================================== Confirmation btn ==============================================
        //==================================================================================================

        confirmationButton = view.findViewById(R.id.confirmationButton);
//        ingredientsTitle = view.findViewById(R.id.ingredientsTitle);
//        checkBox1 = view.findViewById(R.id.checkBox1);
//        checkBox2 = view.findViewById(R.id.checkBox2);
//        checkBox3 = view.findViewById(R.id.checkBox3);
//        checkBox4 = view.findViewById(R.id.checkBox4);
//        checkBox5 = view.findViewById(R.id.checkBox5);
//        checkBox6 = view.findViewById(R.id.checkBox6);
//        checkBox7 = view.findViewById(R.id.checkBox7);

        confirmationButton.setOnClickListener(v -> {

        });




//        breakfastCard = view.findViewById(R.id.breakfastCard);
//        brunchCard = view.findViewById(R.id.brunchCard);
//        lunchCard = view.findViewById(R.id.lunchCard);
//        dinnerCard = view.findViewById(R.id.dinnerCard);

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

//        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // Handle click event for the ImageView in the CardView
//                // You can get the selected image ID with imageIds.get(position)
//            }
//        });


        //==================================================================================================
        //============= OnClickListener for cards that display image of breakfast, brunch, etc =============
        //==================================================================================================
        // Add this code inside the onCreate method, after initializing the horizontal RecyclerView

        CardView breakfastCard = view.findViewById(R.id.breakfastCard);
        TextView breakfastTitle = breakfastCard.findViewById(R.id.mealTypeTitle);
        breakfastTitle.setText("Breakfast");


        CardView lunchCard = view.findViewById(R.id.lunchCard);
        TextView lunchTitle = lunchCard.findViewById(R.id.mealTypeTitle);
        lunchTitle.setText("Lunch");
        CheckBox lunchcheckBox1 = lunchCard.findViewById(R.id.checkBox1);
        lunchcheckBox1.setText("Turkey");
        CheckBox lunchcheckBox2 = lunchCard.findViewById(R.id.checkBox2);
        lunchcheckBox2.setText("Bread");
        CheckBox lunchcheckBox3 = lunchCard.findViewById(R.id.checkBox3);
        lunchcheckBox3.setText("Ham");
        CheckBox lunchcheckBox4 = lunchCard.findViewById(R.id.checkBox4);
        lunchcheckBox4.setText("Lettuce");
        CheckBox lunchcheckBox5 = lunchCard.findViewById(R.id.checkBox5);
        lunchcheckBox5.setText("Cheese");
        CheckBox lunchcheckBox6 = lunchCard.findViewById(R.id.checkBox6);
        lunchcheckBox6.setText("Tuna");
        CheckBox lunchcheckBox7 = lunchCard.findViewById(R.id.checkBox7);
        lunchcheckBox7.setText("Mayonnaise");

        CardView brunchCard = view.findViewById(R.id.brunchCard);
        TextView brunchTitle = brunchCard.findViewById(R.id.mealTypeTitle);
        brunchTitle.setText("Brunch");
        CheckBox brunchcheckBox1 = brunchCard.findViewById(R.id.checkBox1);
        brunchcheckBox1.setText("Eggs");
        CheckBox brunchcheckBox2 = brunchCard.findViewById(R.id.checkBox2);
        brunchcheckBox2.setText("Bacon");
        CheckBox brunchcheckBox3 = brunchCard.findViewById(R.id.checkBox3);
        brunchcheckBox3.setText("Avocado");
        CheckBox brunchcheckBox4 = brunchCard.findViewById(R.id.checkBox4);
        brunchcheckBox4.setText("Tomatoes");
        CheckBox brunchcheckBox5 = brunchCard.findViewById(R.id.checkBox5);
        brunchcheckBox5.setText("Bagels");
        CheckBox brunchcheckBox6 = brunchCard.findViewById(R.id.checkBox6);
        brunchcheckBox6.setText("Smoked salmon");
        CheckBox brunchcheckBox7 = brunchCard.findViewById(R.id.checkBox7);
        brunchcheckBox7.setText("Cream cheese");






        CardView dinnerCard = view.findViewById(R.id.dinnerCard);
        TextView dinnerTitle = dinnerCard.findViewById(R.id.mealTypeTitle);
        dinnerTitle.setText("Dinner");
        CheckBox dinnercheckBox1 = dinnerCard.findViewById(R.id.checkBox1);
        dinnercheckBox1.setText("Rice");
        CheckBox dinnercheckBox2 = dinnerCard.findViewById(R.id.checkBox2);
        dinnercheckBox2.setText("Pasta");
        CheckBox dinnercheckBox3 = dinnerCard.findViewById(R.id.checkBox3);
        dinnercheckBox3.setText("Chicken breast");
        CheckBox dinnercheckBox4 = dinnerCard.findViewById(R.id.checkBox4);
        dinnercheckBox4.setText("Salmon");
        CheckBox dinnercheckBox5 = dinnerCard.findViewById(R.id.checkBox5);
        dinnercheckBox5.setText("Broccoli");
        CheckBox dinnercheckBox6 = dinnerCard.findViewById(R.id.checkBox6);
        dinnercheckBox6.setText("Bell peppers");
        CheckBox dinnercheckBox7 = dinnerCard.findViewById(R.id.checkBox7);
        dinnercheckBox7.setText("Olive oil");

        // Expand the first card view by default
        expandCardView(breakfastCard);


        setMealTypeCardViewClickListener(breakfastCard);
        setMealTypeCardViewClickListener(brunchCard);
        setMealTypeCardViewClickListener(lunchCard);
        setMealTypeCardViewClickListener(dinnerCard);


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
