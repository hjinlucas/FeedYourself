package com.example.feedyourself.main;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.adapters.Review;
import com.example.feedyourself.adapters.User;
import com.example.feedyourself.databinding.ActivityCommentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    private String recipeName;
    private String newImageUrl;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Recipe recipe;
    private ActivityCommentBinding binding;

    private DatabaseReference reviewsDatabaseReference;
    private DatabaseReference recipeDatabaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("RecipeComment");

        reviewsDatabaseReference = FirebaseDatabase.getInstance().getReference("Reviews");
        recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("recipes");
        getUserInfo();


        binding.submitButton.setOnClickListener(v -> {
            uploadReviewsAndMoveBack();
        });

    }
    private void getUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        Log.d(TAG, "onDataChange: user");
//                        TextView userNameTextView = findViewById(R.id.comment_user_name);
//                        ImageView userProfileIcon = findViewById(R.id.comment_user_profile_icon);
//                        TextView recipeName = findViewById(R.id.comment_recipe_name);
                        binding.commentRecipeName.setText(recipe.getName());

                        String userName = dataSnapshot.child("username").getValue(String.class);
                        binding.commentUserName.setText(userName);
                        newImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                        if (newImageUrl != null) {
                            Glide.with(CommentActivity.this)
                                    .load(newImageUrl)
                                    .circleCrop()
                                    .into(binding.commentUserProfileIcon);
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
    }
    private void uploadReviewsAndMoveBack() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String reviewId = reviewsDatabaseReference.push().getKey();
            String comments = binding.commentInput.getText().toString();
            float rating = binding.commentRatingBar.getRating();

            if (reviewId != null) {
                Review review = new Review(reviewId, binding.commentRecipeName.getText().toString(), userId, comments, rating, newImageUrl);
                reviewsDatabaseReference.child(reviewId).setValue(review)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Review successfully uploaded.");
                                finish(); // move back to the previous activity
                            } else {
                                Log.e(TAG, "Failed to upload review.", task.getException());
                            }
                        });



                //update the rating of the recipe
                recipe.setRating(rating);
                recipeDatabaseReference.child(recipe.getId()).setValue(recipe);
            }
        }
    }



}