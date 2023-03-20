package com.example.feedyourself;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView forYouTextView;
    private TextView exploreTextView;
    private TextView premiumTextView;

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
        //============= OnClickListener for cards that display image of breakfast, brunch, etc =============
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
    }
}

