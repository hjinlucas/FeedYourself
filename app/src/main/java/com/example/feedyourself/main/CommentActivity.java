package com.example.feedyourself.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    private String recipeName;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("RecipeComment");
        if (recipe != null) {
            recipeName = recipe.getName();
        }
    }
    private void getUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    User currentUser = dataSnapshot.getValue(User.class);
//                    if (currentUser != null) {
//                        TextView userNameTextView = findViewById(R.id.user_name);
//                        ImageView userProfileIcon = findViewById(R.id.user_profile_icon);
//
//                        userNameTextView.setText(currentUser.getName());
//                        if (currentUser.getProfileIconUrl() != null) {
//                            // Assuming you're using Glide for image loading
//                            Glide.with(CommentActivity.this)
//                                    .load(currentUser.getProfileIconUrl())
//                                    .circleCrop()
//                                    .into(userProfileIcon);
//                        }
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
    }

}