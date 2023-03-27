package com.example.feedyourself;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Set the duration for the welcome page (e.g., 2000 milliseconds = 2 seconds)
        int welcomePageDuration = 1200;

        new Handler().postDelayed(() -> {
            // Start the main activity after the specified duration
            Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, welcomePageDuration);
    }

}

