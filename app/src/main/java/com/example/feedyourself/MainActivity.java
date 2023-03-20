package com.example.feedyourself;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private TextView forYouTextView;
    private TextView exploreTextView;
    private TextView premiumTextView;

    private TextView breakfastTextView;
    private TextView brunchTextView;
    private TextView lunchTextView;
    private TextView dinnerTextView;
    private FloatingActionButton confirmationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //==================================================================================================
        //============= OnClickListener for small menu in main page ========================================
        //==================================================================================================
        forYouTextView = findViewById(R.id.forYouTextView);
        exploreTextView = findViewById(R.id.exploreTextView);
        premiumTextView = findViewById(R.id.premiumTextView);

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
        RecyclerView horizontalRecyclerView = findViewById(R.id.horizontalRecyclerView);

        List<Integer> imageIds = Arrays.asList(
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background
                //just for test now, replace image later
        );

        ImageAdapter imageAdapter = new ImageAdapter(this, imageIds);
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

        breakfastTextView = findViewById(R.id.breakfastTextView);
        brunchTextView = findViewById(R.id.brunchTextView);
        lunchTextView = findViewById(R.id.lunchTextView);
        dinnerTextView = findViewById(R.id.dinnerTextView);
        confirmationButton = findViewById(R.id.confirmationButton);

        View.OnClickListener mealTypeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform any action you want when a meal type is selected

                // Show the confirmation button when a meal type is selected
                confirmationButton.setVisibility(View.VISIBLE);
            }
        };

        breakfastTextView.setOnClickListener(mealTypeClickListener);
        brunchTextView.setOnClickListener(mealTypeClickListener);
        lunchTextView.setOnClickListener(mealTypeClickListener);
        dinnerTextView.setOnClickListener(mealTypeClickListener);

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event for the confirmation button
                // For example, navigate to the next page with the selected meal type
            }
        });

    }
}

