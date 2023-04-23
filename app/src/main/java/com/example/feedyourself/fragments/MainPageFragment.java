package com.example.feedyourself.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.adapters.ImageAdapter;
import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.utils.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.viewpager2.widget.ViewPager2;


public class MainPageFragment extends Fragment {

    private RecyclerView horizontalRecyclerView;
//    private CardView breakfastCard, brunchCard, lunchCard, dinnerCard;
    private FloatingActionButton confirmationButton;
//    private TextView ingredientsTitle;
    private int expandedPosition = 0;
    private int clickedPosition;

//    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    private List<Recipe> allRecipes = new ArrayList<>();

    private List<CheckBox> BrecheckBoxes = new ArrayList<>();
    private List<CheckBox> LuncheckBoxes = new ArrayList<>();
    private List<CheckBox> BrucheckBoxes = new ArrayList<>();
    private List<CheckBox> DincheckBoxes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Reset the state of all the checkboxes to unchecked
        for (CheckBox checkBox : BrecheckBoxes) {
            checkBox.setChecked(false);
        }

        for (CheckBox checkBox : LuncheckBoxes) {
            checkBox.setChecked(false);
        }

        for (CheckBox checkBox : BrucheckBoxes) {
            checkBox.setChecked(false);
        }

        for (CheckBox checkBox : DincheckBoxes) {
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CardView card1 = view.findViewById(R.id.cardImageView);
//        ScrollView scrollView = view.findViewById(R.id.scrollView1);
//        OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
//        scrollView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    scrollView.setEdgeEffectFactory(new EdgeEffectFactory() {
//                        @NonNull
//                        @Override
//                        public EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
//                            return new CustomEdgeEffect(view.getContext());
//                        }
//                    });
//                }
//            }
//        });
//
//        scrollView.setHorizontalScrollBarEnabled(false);
//        scrollView.setVerticalScrollBarEnabled(false);


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
        ViewPager2 viewPager =  view.findViewById(R.id.viewPager);
        ProgressBar scrollBar = view.findViewById(R.id.scrollBar);

        List<Integer> imageIds = Arrays.asList(
                R.drawable.hori1,
                R.drawable.hori2,
                R.drawable.hori3,
                R.drawable.hori4
                //just for test now, replace image later
        );

        ImageAdapter adapter = new ImageAdapter(imageIds);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                int totalPages = viewPager.getAdapter().getItemCount();
                float progress = ((float) position + positionOffset) / (totalPages - 1);
                scrollBar.setProgress((int) (progress * scrollBar.getMax()));
            }
        });

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





        CardView lunchCard = view.findViewById(R.id.lunchCard);
        ImageView lunchBackground = lunchCard.findViewById(R.id.backgroundImage);
        lunchBackground.setImageResource(R.drawable.lunch);
        TextView lunchTitle = lunchCard.findViewById(R.id.mealTypeTitle);
        lunchTitle.setText("Lunch");
        CheckBox lunchcheckBox1 = lunchCard.findViewById(R.id.checkBox1);
        lunchcheckBox1.setText("Canned Tunna");
        LuncheckBoxes.add(lunchcheckBox1);
        CheckBox lunchcheckBox2 = lunchCard.findViewById(R.id.checkBox2);
        lunchcheckBox2.setText("Sliced Bread");
        LuncheckBoxes.add(lunchcheckBox2);
        CheckBox lunchcheckBox3 = lunchCard.findViewById(R.id.checkBox3);
        lunchcheckBox3.setText("Ham");
        LuncheckBoxes.add(lunchcheckBox3);
        CheckBox lunchcheckBox4 = lunchCard.findViewById(R.id.checkBox4);
        lunchcheckBox4.setText("Turkey");
        LuncheckBoxes.add(lunchcheckBox4);
        CheckBox lunchcheckBox5 = lunchCard.findViewById(R.id.checkBox5);
        lunchcheckBox5.setText("Spicy");
        LuncheckBoxes.add(lunchcheckBox5);
        CheckBox lunchcheckBox6 = lunchCard.findViewById(R.id.checkBox6);
        lunchcheckBox6.setText("Savory");
        LuncheckBoxes.add(lunchcheckBox6);
        CheckBox lunchcheckBox7 = lunchCard.findViewById(R.id.checkBox7);
        lunchcheckBox7.setText("Sweet");
        LuncheckBoxes.add(lunchcheckBox7);



        CardView brunchCard = view.findViewById(R.id.brunchCard);
        ImageView brunchBackground = brunchCard.findViewById(R.id.backgroundImage);
        brunchBackground.setImageResource(R.drawable.brunch);
        TextView brunchTitle = brunchCard.findViewById(R.id.mealTypeTitle);
        brunchTitle.setText("Brunch");

        CheckBox brunchcheckBox1 = brunchCard.findViewById(R.id.checkBox1);
        brunchcheckBox1.setText("Eggs");
        BrucheckBoxes.add(brunchcheckBox1);

        CheckBox brunchcheckBox2 = brunchCard.findViewById(R.id.checkBox2);
        brunchcheckBox2.setText("Pepper");
        BrucheckBoxes.add(brunchcheckBox2);


        CheckBox brunchcheckBox3 = brunchCard.findViewById(R.id.checkBox3);
        brunchcheckBox3.setText("Green onions");
        BrucheckBoxes.add(brunchcheckBox3);

        CheckBox brunchcheckBox4 = brunchCard.findViewById(R.id.checkBox4);
        brunchcheckBox4.setText("Cheddar Cheese");
        BrucheckBoxes.add(brunchcheckBox4);

        CheckBox brunchcheckBox5 = brunchCard.findViewById(R.id.checkBox5);
        brunchcheckBox5.setText("Savory");
        BrucheckBoxes.add(brunchcheckBox5);

        CheckBox brunchcheckBox6 = brunchCard.findViewById(R.id.checkBox6);
        brunchcheckBox6.setText("Spicy");
        BrucheckBoxes.add(brunchcheckBox6);


        CheckBox brunchcheckBox7 = brunchCard.findViewById(R.id.checkBox7);
        brunchcheckBox7.setText("Sweet");
        BrucheckBoxes.add(brunchcheckBox7);






        CardView dinnerCard = view.findViewById(R.id.dinnerCard);
        ImageView dinnerBackground = dinnerCard.findViewById(R.id.backgroundImage);
        dinnerBackground.setImageResource(R.drawable.dinner);
        TextView dinnerTitle = dinnerCard.findViewById(R.id.mealTypeTitle);
        dinnerTitle.setText("Dinner");
        CheckBox dinnercheckBox1 = dinnerCard.findViewById(R.id.checkBox1);
        dinnercheckBox1.setText("Bacon");
        DincheckBoxes.add(dinnercheckBox1);

        CheckBox dinnercheckBox2 = dinnerCard.findViewById(R.id.checkBox2);
        dinnercheckBox2.setText("Sliced Bread");
        DincheckBoxes.add(dinnercheckBox2);

        CheckBox dinnercheckBox3 = dinnerCard.findViewById(R.id.checkBox3);
        dinnercheckBox3.setText("Lettuce");
        DincheckBoxes.add(dinnercheckBox3);

        CheckBox dinnercheckBox4 = dinnerCard.findViewById(R.id.checkBox4);
        dinnercheckBox4.setText("Tomato");
        DincheckBoxes.add(dinnercheckBox4);

        CheckBox dinnercheckBox5 = dinnerCard.findViewById(R.id.checkBox5);
        dinnercheckBox5.setText("Savory");
        DincheckBoxes.add(dinnercheckBox5);

        CheckBox dinnercheckBox6 = dinnerCard.findViewById(R.id.checkBox6);
        dinnercheckBox6.setText("Sweet");
        DincheckBoxes.add(dinnercheckBox6);

        CheckBox dinnercheckBox7 = dinnerCard.findViewById(R.id.checkBox7);
        dinnercheckBox7.setText("Sweet");
        DincheckBoxes.add(dinnercheckBox7);

        // Expand the first card view by default
        expandCardView(breakfastCard);


        setMealTypeCardViewClickListener(breakfastCard);
        setMealTypeCardViewClickListener(brunchCard);
        setMealTypeCardViewClickListener(lunchCard);
        setMealTypeCardViewClickListener(dinnerCard);
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        confirmationButton.setOnClickListener(v -> {
            switch (clickedPosition){
                case 0:
                    if(!breakfastcheckBox1.isChecked() && !breakfastcheckBox2.isChecked()&& !breakfastcheckBox3.isChecked()&& !breakfastcheckBox4.isChecked() &&!breakfastcheckBox5.isChecked() &&!breakfastcheckBox6.isChecked()&& !breakfastcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        List<String> ingredients = new ArrayList<>();
                        for (CheckBox box:BrecheckBoxes
                             ) {
                            if(box.isChecked()){
                                ingredients.add(box.getText().toString());
                            }
                        }
                        sharedViewModel.setIngredients("Breakfast", ingredients);

                        loadFragment(new SelectRecipeFragment());

                    }
                    break;

                case 1:
                    if(!brunchcheckBox1.isChecked() && !brunchcheckBox2.isChecked()&& !brunchcheckBox3.isChecked()&& !brunchcheckBox4.isChecked() &&!brunchcheckBox5.isChecked() &&!brunchcheckBox6.isChecked()&& !brunchcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        List<String> ingredients = new ArrayList<>();
                        for (CheckBox box:BrucheckBoxes
                        ) {
                            if(box.isChecked()){
                                ingredients.add(box.getText().toString());
                            }
                        }
                        sharedViewModel.setIngredients("Brunch", ingredients);
                        sharedViewModel.getIngredients().postValue(sharedViewModel.getIngredients().getValue());
                        loadFragment(new SelectRecipeFragment());
                    }
                    break;
                case 2:
                    if(!lunchcheckBox1.isChecked() && !lunchcheckBox2.isChecked()&& !lunchcheckBox3.isChecked()&& !lunchcheckBox4.isChecked() &&!lunchcheckBox5.isChecked() &&!lunchcheckBox6.isChecked()&& !lunchcheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        List<String> ingredients = new ArrayList<>();
                        for (CheckBox box:LuncheckBoxes
                        ) {
                            if(box.isChecked()){
                                ingredients.add(box.getText().toString());
                            }
                        }
                        sharedViewModel.setIngredients("Lunch", ingredients);
                        sharedViewModel.getIngredients().postValue(sharedViewModel.getIngredients().getValue());
                        loadFragment(new SelectRecipeFragment());
                    }
                    break;

                case 3:
                    if(!dinnercheckBox1.isChecked() && !dinnercheckBox2.isChecked()&& !dinnercheckBox3.isChecked()&& !dinnercheckBox4.isChecked() &&!dinnercheckBox5.isChecked() &&!dinnercheckBox6.isChecked()&& !dinnercheckBox7.isChecked()){
                        Toast.makeText(requireContext(), "You must select at least one ingredients to start", Toast.LENGTH_SHORT).show();
                    }else{
                        List<String> ingredients = new ArrayList<>();
                        for (CheckBox box:DincheckBoxes
                        ) {
                            if(box.isChecked()){
                                ingredients.add(box.getText().toString());
                            }
                        }
                        sharedViewModel.setIngredients("Dinner", ingredients);
                        sharedViewModel.getIngredients().postValue(sharedViewModel.getIngredients().getValue());
                        loadFragment(new SelectRecipeFragment());
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
