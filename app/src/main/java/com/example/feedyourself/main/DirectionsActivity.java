package com.example.feedyourself.main;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.databinding.ActivityDirectionsBinding;
import com.google.android.material.appbar.AppBarLayout;

public class DirectionsActivity extends AppCompatActivity {
    private ActivityDirectionsBinding binding;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        binding.recipeDirectionImage.setImageResource(recipe.getImgId());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset <= scrollRange / 2) { // Adjust the value to show the title sooner or later
                    binding.collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    binding.collapsingToolbarLayout.setTitleEnabled(true);
                    isShow = true;
                } else if (isShow) {
                    binding.collapsingToolbarLayout.setTitleEnabled(false);
                    isShow = false;
                }
            }
        });
        binding.commentIcon.setOnClickListener(v -> {
            leaveComments();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dir_act_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void leaveComments(){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("RecipeComment", recipe);
        startActivity(intent);
    }
}