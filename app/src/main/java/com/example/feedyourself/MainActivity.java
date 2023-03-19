package com.example.feedyourself;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView forYouTextView;
    private TextView exploreTextView;
    private TextView premiumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}

