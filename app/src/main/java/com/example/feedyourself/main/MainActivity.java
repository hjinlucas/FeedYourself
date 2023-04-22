package com.example.feedyourself.main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.fragments.MainPageFragment;
import com.example.feedyourself.fragments.SavedFragment;
import com.example.feedyourself.fragments.SearchFragment;
import com.example.feedyourself.fragments.RegisterFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity {


    private FloatingActionButton confirmationButton;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
        loadFragment(new MainPageFragment());

        //create recipes in firebase RTDB
        createAndStoreRecipes();


    }

    private void createAndStoreRecipes() {
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        // Create some recipes


        List<String> ingredients11 = Arrays.asList("sliced bread", "turkey", "cheddar cheese", "lettuce", "tomato", "mayonnaise");
        Recipe recipe11 = new Recipe("11", "Turkey and Cheese Sandwich", "Lunch", ingredients11, "", false, 300, 10, "#Spread mayonnaise on one side of each slice of bread.#Layer turkey, cheddar cheese, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe11.getId()).setValue(recipe11);

        List<String> ingredients12 = Arrays.asList("sliced bread", "ham", "swiss cheese", "butter", "dijon mustard");
        Recipe recipe12 = new Recipe("12", "Ham and Cheese Panini", "Lunch", ingredients12, "", false, 400, 15, "#Spread butter on one side of each slice of bread.#On the other side of one slice, spread dijon mustard.#Layer ham and swiss cheese on top of the mustard.#Place the second slice of bread on top, butter-side out.#Heat a skillet or panini press over medium heat.#Cook the sandwich for 3-4 minutes on each side or until bread is golden brown and cheese is melted.#Serve immediately.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe12.getId()).setValue(recipe12);

        List<String> ingredients13 = Arrays.asList("canned tuna", "mayonnaise", "diced celery", "diced onion", "salt", "pepper", "sliced bread", "lettuce");
        Recipe recipe13 = new Recipe("13", "Tuna Salad Sandwich", "Lunch", ingredients13, "", false, 400, 15, "#In a medium bowl, mix together tuna, mayonnaise, diced celery, diced onion, salt, and pepper until well combined.#Spread the tuna salad onto a slice of bread.#Top with lettuce and another slice of bread.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe13.getId()).setValue(recipe13);

        List<String> ingredients14 = Arrays.asList("sliced bread", "bacon", "lettuce", "tomato", "mayonnaise");
        Recipe recipe14 = new Recipe("14", "Classic BLT Sandwich", "Lunch", ingredients14, "", false, 400, 20, "#Cook bacon in a skillet over medium heat until crispy. Set aside on paper towels to drain.#Spread mayonnaise on one side of each slice of bread.#Layer bacon, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe14.getId()).setValue(recipe14);


        List<Recipe> recipes = Arrays.asList(recipe11, recipe12, recipe13, recipe14);
        List<String> imagePaths = Arrays.asList("id11.png", "id12.png", "id13.png", "id14.png");

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            String imagePath = imagePaths.get(i);

            recipesRef.child(recipe.getId()).setValue(recipe);

            //if you want to upload several images for one recipe at the same time
            //just modify recipe class String to List<String>, as well as the imageUrls here.
            uploadImage(recipe, recipesRef, imagePath, new OnImageUploadedListener() {
                @Override
                public void onImageUploaded(Recipe recipe, DatabaseReference recipesRef, String imageUrl) {
                    recipe.setImageUrls(imageUrl);
                    recipesRef.child(recipe.getId()).setValue(recipe);
                }
            });

        }

    }



    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_main_page:
                    selectedFragment = new MainPageFragment();
                    break;
                case R.id.nav_find:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_saved_list:
                    selectedFragment = new SavedFragment();
                    break;
                case R.id.nav_user:
                    selectedFragment = new RegisterFragment();
                    break;

            }

            if (selectedFragment != null) {
                return loadFragment(selectedFragment);
            } else {
                return false;
            }
        });
    }

    protected boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

//    private void openRegisterFragment() {
//        Fragment registerFragment = new RegisterFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, registerFragment);
//        transaction.commit();
//    }


    public void uploadImage(Recipe recipe, DatabaseReference recipesRef, String imagePath, final OnImageUploadedListener listener) {
        String recipeId = recipe.getId();
        StorageReference recipeImagesRef = storageRef.child("images/" + recipeId);
        StorageReference imageRef = recipeImagesRef.child(imagePath);

        try {
            InputStream inputStream = getAssets().open(imagePath);

            imageRef.putStream(inputStream)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    listener.onImageUploaded(recipe, recipesRef, imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("TAG", "Error uploading image", exception);
                        }
                    });
        } catch (IOException e) {
            Log.e("TAG", "Error opening image file", e);
        }
    }


    public interface OnImageUploadedListener {
        void onImageUploaded(Recipe recipe, DatabaseReference recipesRef, String imageUrl);
    }





}

