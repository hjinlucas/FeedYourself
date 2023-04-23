package com.example.feedyourself.main;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.Review;
import com.example.feedyourself.adapters.ReviewAdapter;
import com.example.feedyourself.databinding.ActivityDirectionsBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DirectionsActivity extends AppCompatActivity {
    private ActivityDirectionsBinding binding;
    private Recipe recipe;
    private ReviewAdapter reviewAdapter;

    private List<Review> reviewList = new ArrayList<>();
    private DatabaseReference reviewRef;
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
        createRecyclerView();

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
    private void createRecyclerView(){
        reviewRef= FirebaseDatabase.getInstance().getReference("Reviews");
        reviewAdapter = new ReviewAdapter(this, reviewList);
        binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.commentsRecyclerView.setAdapter(reviewAdapter);

        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
//                            if(recipe.getIngredients().retainAll(mealTypeIngredients)){//It means there are duplicates between the two lists
//                                recipeList.add(recipe);
//
//                            }
                    reviewList.add(review);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}