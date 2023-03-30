package com.example.feedyourself.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feedyourself.R;

public class WelcomeActivity extends AppCompatActivity {

    Animation topAnim;
    ImageView imageView;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        // Set the duration for the welcome page (e.g., 2000 milliseconds = 2 seconds)
        int welcomePageDuration = 1600;

        new Handler().postDelayed(() -> {
            // Start the main activity after the specified duration
            Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, welcomePageDuration);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        //Hooks
        imageView = findViewById(R.id.welcome_image);

        imageView.setAnimation(topAnim);
    }

}

