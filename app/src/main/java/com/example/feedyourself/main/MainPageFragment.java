package com.example.feedyourself.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.ImageAdapter;
import com.example.feedyourself.R;
import com.example.feedyourself.Recipe;
import com.example.feedyourself.utils.SharedViewModel;
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
    private int clickedPosition;

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
        List<CheckBox> BrecheckBoxes = new ArrayList<>();
        CheckBox breakfastcheckBox1 = breakfastCard.findViewById(R.id.checkBox1);
        BrecheckBoxes.add(breakfastcheckBox1);
        CheckBox breakfastcheckBox2 = breakfastCard.findViewById(R.id.checkBox2);
        BrecheckBoxes.add(breakfastcheckBox2);
        CheckBox breakfastcheckBox3 = breakfastCard.findViewById(R.id.checkBox3);
        BrecheckBoxes.add(breakfastcheckBox3);
        CheckBox breakfastcheckBox4 = breakfastCard.findViewById(R.id.checkBox4);
        BrecheckBoxes.add(breakfastcheckBox4);
        CheckBox breakfastcheckBox5 = breakfastCard.findViewById(R.id.checkBox5);
        BrecheckBoxes.add(breakfastcheckBox5);
        CheckBox breakfastcheckBox6 = breakfastCard.findViewById(R.id.checkBox6);
        BrecheckBoxes.add(breakfastcheckBox6);
        CheckBox breakfastcheckBox7 = breakfastCard.findViewById(R.id.checkBox7);
        BrecheckBoxes.add(breakfastcheckBox7);




        List<CheckBox> LuncheckBoxes = new ArrayList<>();
        CardView lunchCard = view.findViewById(R.id.lunchCard);
        TextView lunchTitle = lunchCard.findViewById(R.id.mealTypeTitle);
        lunchTitle.setText("Lunch");
        CheckBox lunchcheckBox1 = lunchCard.findViewById(R.id.checkBox1);
        lunchcheckBox1.setText("Turkey");
        LuncheckBoxes.add(lunchcheckBox1);
        CheckBox lunchcheckBox2 = lunchCard.findViewById(R.id.checkBox2);
        lunchcheckBox2.setText("Bread");
        LuncheckBoxes.add(lunchcheckBox2);
        CheckBox lunchcheckBox3 = lunchCard.findViewById(R.id.checkBox3);
        lunchcheckBox3.setText("Ham");
        LuncheckBoxes.add(lunchcheckBox3);
        CheckBox lunchcheckBox4 = lunchCard.findViewById(R.id.checkBox4);
        lunchcheckBox4.setText("Lettuce");
        LuncheckBoxes.add(lunchcheckBox4);
        CheckBox lunchcheckBox5 = lunchCard.findViewById(R.id.checkBox5);
        lunchcheckBox5.setText("Cheese");
        LuncheckBoxes.add(lunchcheckBox5);
        CheckBox lunchcheckBox6 = lunchCard.findViewById(R.id.checkBox6);
        lunchcheckBox6.setText("Tuna");
        LuncheckBoxes.add(lunchcheckBox6);
        CheckBox lunchcheckBox7 = lunchCard.findViewById(R.id.checkBox7);
        lunchcheckBox7.setText("Mayonnaise");
        LuncheckBoxes.add(lunchcheckBox7);


        List<CheckBox> BrucheckBoxes = new ArrayList<>();
        CardView brunchCard = view.findViewById(R.id.brunchCard);
        TextView brunchTitle = brunchCard.findViewById(R.id.mealTypeTitle);
        brunchTitle.setText("Brunch");

        CheckBox brunchcheckBox1 = brunchCard.findViewById(R.id.checkBox1);
        brunchcheckBox1.setText("Eggs");
        BrucheckBoxes.add(brunchcheckBox1);

        CheckBox brunchcheckBox2 = brunchCard.findViewById(R.id.checkBox2);
        brunchcheckBox2.setText("Bacon");
        BrucheckBoxes.add(brunchcheckBox2);


        CheckBox brunchcheckBox3 = brunchCard.findViewById(R.id.checkBox3);
        brunchcheckBox3.setText("Avocado");
        BrucheckBoxes.add(brunchcheckBox3);

        CheckBox brunchcheckBox4 = brunchCard.findViewById(R.id.checkBox4);
        brunchcheckBox4.setText("Tomatoes");
        BrucheckBoxes.add(brunchcheckBox4);

        CheckBox brunchcheckBox5 = brunchCard.findViewById(R.id.checkBox5);
        brunchcheckBox5.setText("Bagels");
        BrucheckBoxes.add(brunchcheckBox5);

        CheckBox brunchcheckBox6 = brunchCard.findViewById(R.id.checkBox6);
        brunchcheckBox6.setText("Smoked salmon");
        BrucheckBoxes.add(brunchcheckBox6);


        CheckBox brunchcheckBox7 = brunchCard.findViewById(R.id.checkBox7);
        brunchcheckBox7.setText("Cream cheese");
        BrucheckBoxes.add(brunchcheckBox7);





        List<CheckBox> DincheckBoxes = new ArrayList<>();
        CardView dinnerCard = view.findViewById(R.id.dinnerCard);
        TextView dinnerTitle = dinnerCard.findViewById(R.id.mealTypeTitle);
        dinnerTitle.setText("Dinner");
        CheckBox dinnercheckBox1 = dinnerCard.findViewById(R.id.checkBox1);
        dinnercheckBox1.setText("Rice");
        DincheckBoxes.add(dinnercheckBox1);

        CheckBox dinnercheckBox2 = dinnerCard.findViewById(R.id.checkBox2);
        dinnercheckBox2.setText("Pasta");
        DincheckBoxes.add(dinnercheckBox2);

        CheckBox dinnercheckBox3 = dinnerCard.findViewById(R.id.checkBox3);
        dinnercheckBox3.setText("Chicken breast");
        DincheckBoxes.add(dinnercheckBox3);

        CheckBox dinnercheckBox4 = dinnerCard.findViewById(R.id.checkBox4);
        dinnercheckBox4.setText("Salmon");
        DincheckBoxes.add(dinnercheckBox4);

        CheckBox dinnercheckBox5 = dinnerCard.findViewById(R.id.checkBox5);
        dinnercheckBox5.setText("Broccoli");
        DincheckBoxes.add(dinnercheckBox5);

        CheckBox dinnercheckBox6 = dinnerCard.findViewById(R.id.checkBox6);
        dinnercheckBox6.setText("Bell peppers");
        DincheckBoxes.add(dinnercheckBox6);

        CheckBox dinnercheckBox7 = dinnerCard.findViewById(R.id.checkBox7);
        dinnercheckBox7.setText("Olive oil");
        DincheckBoxes.add(dinnercheckBox7);

        // Expand the first card view by default
        expandCardView(breakfastCard);


        setMealTypeCardViewClickListener(breakfastCard);
        setMealTypeCardViewClickListener(brunchCard);
        setMealTypeCardViewClickListener(lunchCard);
        setMealTypeCardViewClickListener(dinnerCard);
        SharedViewModel<String, Boolean> sharedViewModel = new SharedViewModel<>();
        confirmationButton.setOnClickListener(v -> {
            switch (clickedPosition){
                case 0:
                    if(!breakfastcheckBox1.isChecked() && !breakfastcheckBox2.isChecked()&& !breakfastcheckBox3.isChecked()&& !breakfastcheckBox4.isChecked() &&!breakfastcheckBox5.isChecked() &&!breakfastcheckBox6.isChecked()&& !breakfastcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{

                        for (CheckBox box:BrecheckBoxes
                             ) {
                            if(box.isChecked()){
                                sharedViewModel.add(box.getText().toString(), true);
                                loadFragment(new SelectRecipeFragment());
                            }
                        }
                    }
                    break;

                case 1:
                    if(!brunchcheckBox1.isChecked() && !brunchcheckBox2.isChecked()&& !brunchcheckBox3.isChecked()&& !brunchcheckBox4.isChecked() &&!brunchcheckBox5.isChecked() &&!brunchcheckBox6.isChecked()&& !brunchcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        for (CheckBox box:BrucheckBoxes
                        ) {
                            if(box.isChecked()){
                                sharedViewModel.add(box.getText().toString(), true);
                                loadFragment(new SelectRecipeFragment());
                            }
                        }
                    }
                    break;
                case 2:
                    if(!lunchcheckBox1.isChecked() && !lunchcheckBox2.isChecked()&& !lunchcheckBox3.isChecked()&& !lunchcheckBox4.isChecked() &&!lunchcheckBox5.isChecked() &&!lunchcheckBox6.isChecked()&& !lunchcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        for (CheckBox box:LuncheckBoxes
                        ) {
                            if(box.isChecked()){
                                sharedViewModel.add(box.getText().toString(), true);
                                loadFragment(new SelectRecipeFragment());
                            }
                        }
                    }
                    break;

                case 3:
                    if(!dinnercheckBox1.isChecked() && !dinnercheckBox2.isChecked()&& !dinnercheckBox3.isChecked()&& !dinnercheckBox4.isChecked() &&!dinnercheckBox5.isChecked() &&!dinnercheckBox6.isChecked()&& !dinnercheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        for (CheckBox box:DincheckBoxes
                        ) {
                            if(box.isChecked()){
                                sharedViewModel.add(box.getText().toString(), true);
                                loadFragment(new SelectRecipeFragment());
                            }
                        }
                    }
                    break;
            }
        });


    }


    private void setMealTypeCardViewClickListener(CardView cardView) {
        cardView.setOnClickListener(v -> {
            clickedPosition = ((ViewGroup) cardView.getParent()).indexOfChild(cardView);
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
    protected boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            return true;
        }
        return false;
    }




}
