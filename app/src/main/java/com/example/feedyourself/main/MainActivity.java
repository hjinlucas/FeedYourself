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
        List<String> ingredients2 = Arrays.asList("onion", "milk", "tomatoes", "cheddar cheese", "Sausage");
        Recipe recipe2 = new Recipe("2", "Sausage and Cheese Pie", "Breakfast", ingredients2, "savory", false, 500, 90, "Preheat oven to 400°F.#Cook sausage and onion in a large saucepan over medium-high heat 8 to 10 minutes or until thoroughly cooked, stirring frequently; drain. Stir in salt and pepper. Spoon sausage mixture into a lightly greased, 9-inch glass pie plate.#Combine milk, baking mix and eggs in a medium bowl and blend with a wire whisk. Pour batter over sausage in pie plate.#Bake 25 minutes or until knife inserted in center comes out clean. Remove from oven. Top with sliced tomato and sprinkle with cheese. Bake an additional 5 minutes or until cheese is melted.#Let stand minutes before serving.",R.drawable.cheese_pie, 4);
        recipesRef.child(recipe2.getId()).setValue(recipe2);
        // Create some recipes
        List<String> ingredients3 = Arrays.asList("beef","onion","black pepper","carrot","parsnip","potato","flour","hot water");
        Recipe recipe3 = new Recipe("3", "Stewed Beef Brisket with Potatoes", "Dinner", ingredients3, "savory", false, 1000, 180, "Cut the brisket into 2-inch cubes. Pat dry with paper towels. If you do not dry the meat, it will not brown properly. Heat the olive oil in a Dutch oven over medium to high heat. Cook in batches, so you do not overcrowd the meat, until all the meat is browned. Use a pair of tongs to turn the meat. As it browns, remove from the pot and place on a large plate. Season generously with salt and black pepper. #\u200BPlace the browned meat in the slow cooker. De-glaze the pan with the red wine, using a wooden spoon to scrape up any browned on bits. Scrape everything into the slow cooker. ",R.drawable.stewed_beef_brisket_with_potatoes, 4);
        recipesRef.child(recipe3.getId()).setValue(recipe3);

        List<String> ingredients4 = Arrays.asList("eggs","bacon","cheddar cheese","milk","salt","pepper","green onions");
        Recipe recipe4 = new Recipe("4", "Bacon and Egg Muffins", "Brunch", ingredients4, "savory", false, 400, 30, "Preheat oven to 350°F. Grease a 12-cup muffin tin.#Cook bacon in a skillet over medium heat until crispy. Set aside to cool, then crumble into small pieces.#In a large bowl, whisk together eggs, milk, salt, and pepper. Stir in crumbled bacon, cheddar cheese, and chopped green onions.#Spoon the egg mixture into the prepared muffin tin, filling each cup about ⅔ full.#Bake for 20-25 minutes or until the egg muffins are set and cooked through. Allow to cool for a few minutes before removing from the tin.",R.drawable.bacon_egg_muffins, 5);
        recipesRef.child(recipe4.getId()).setValue(recipe4);

        List<String> ingredients5 = Arrays.asList("eggs","milk","sausage","onion","pie crust","cheddar cheese","salt","pepper");
        Recipe recipe5 = new Recipe("5", "Sausage and Onion Quiche", "Breakfast", ingredients5, "savory", false, 460, 60, "PPreheat oven to 375°F. Place pie crust in a 9-inch pie plate, crimp edges and set aside.#In a skillet, cook sausage and onion over medium heat until sausage is cooked through and onions are softened. Drain and set aside.#In a large bowl, whisk together eggs, milk, salt, and pepper. Stir in the sausage mixture and cheddar cheese.#Pour the egg mixture into the pie crust. Bake for 35-40 minutes or until the quiche is set and golden brown. Allow to cool for a few minutes before slicing and serving.",R.drawable.sausage_and_onion_quiche, 4);
        recipesRef.child(recipe5.getId()).setValue(recipe5);

        List<String> ingredients6 = Arrays.asList("sliced bread","tomatoes","cheddar cheese","butter","lettuce","cheddar cheese","mayonnaise");
        Recipe recipe6 = new Recipe("6", "Tomato and Cheese Breakfast Sandwich", "Breakfast", ingredients6, "savory", false, 300, 10, "Spread butter on one side of each slice of bread. Heat a skillet over medium heat.#Place bread slices, buttered-side down, on the skillet. Top each slice with cheddar cheese and cook until cheese is melted and bread is golden brown.#Remove from skillet and place sliced tomatoes and lettuce on one slice of bread. Spread mayonnaise on the other slice of bread. Press the two slices together to form the sandwich. Serve immediately.",R.drawable.tomato_and_cheese_breakfast_sandwich, 5);
        recipesRef.child(recipe6.getId()).setValue(recipe6);

        List<String> ingredients7 = Arrays.asList("frozen hash browns","bacon","onion","cheddar cheese","butter","cheddar cheese","salt","pepper");
        Recipe recipe7 = new Recipe("7", "Cheesy Bacon and Onion Hash Browns", "Breakfast", ingredients7, "savory", false, 450, 30, "SCook bacon in a skillet over medium heat until crispy. Set aside to cool, then crumble into small pieces.#In the same skillet, melt butter and cook onions until softened. Add frozen hash browns, salt, and pepper. Cook until hash browns are golden brown and crispy.#Sprinkle crumbled bacon and cheddar cheese over the hash browns. Cover and cook for an additional 2-3 minutes or until cheese is melted. Serve immediately.",R.drawable.cheesy_bacon_onion_hash_browns, 3);
        recipesRef.child(recipe7.getId()).setValue(recipe7);

        List<String> ingredients8 = Arrays.asList("sausage","eggs","milk","cheddar cheese","flour tortillas","onion","green bell pepper","salsa","salt","pepper");
        Recipe recipe8 = new Recipe("8", "Sausage, Egg, and Cheese Breakfast Burritos", "Breakfast", ingredients8, "savory", false, 560, 25, "Cook sausage in a skillet over medium heat, breaking it apart as it cooks. Once cooked, remove sausage from skillet and set aside.#In the same skillet, cook onion and green bell pepper until softened. In a bowl, whisk together eggs, milk, salt, and pepper. Pour the egg mixture into the skillet with the vegetables and cook, stirring occasionally, until eggs are cooked through.#Add cooked sausage and cheddar cheese to the egg mixture, stirring until cheese is melted. Warm flour tortillas in the microwave for about 15 seconds.#Spoon the sausage, egg, and cheese mixture onto the center of each tortilla. Add a spoonful of salsa, then fold the sides and roll up the tortilla to form a burrito. Serve immediately.",R.drawable.sausage_egg_cheese_breakfast_burritos, 5);
        recipesRef.child(recipe8.getId()).setValue(recipe8);


        List<String> ingredients11 = Arrays.asList("sliced bread", "turkey", "cheddar cheese", "lettuce", "tomato", "mayonnaise");
        Recipe recipe11 = new Recipe("11", "Turkey and Cheese Sandwich", "Lunch", ingredients11, "savory", false, 300, 10, "Spread mayonnaise on one side of each slice of bread.#Layer turkey, cheddar cheese, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.turkey_and_cheese_sandwich,2);
        recipesRef.child(recipe11.getId()).setValue(recipe11);

        List<String> ingredients12 = Arrays.asList("sliced bread", "ham", "swiss cheese", "butter", "dijon mustard");
        Recipe recipe12 = new Recipe("12", "Ham and Cheese Panini", "Lunch", ingredients12, "sweet", false, 400, 15, "Spread butter on one side of each slice of bread.#On the other side of one slice, spread dijon mustard.#Layer ham and swiss cheese on top of the mustard.#Place the second slice of bread on top, butter-side out.#Heat a skillet or panini press over medium heat.#Cook the sandwich for 3-4 minutes on each side or until bread is golden brown and cheese is melted.#Serve immediately.", R.drawable.ham_and_cheese_panini,3);
        recipesRef.child(recipe12.getId()).setValue(recipe12);

        List<String> ingredients13 = Arrays.asList("canned tuna", "mayonnaise", "diced celery", "diced onion", "salt", "pepper", "sliced bread", "lettuce");
        Recipe recipe13 = new Recipe("13", "Tuna Salad Sandwich", "Lunch", ingredients13, "sweet", false, 400, 15, "In a medium bowl, mix together tuna, mayonnaise, diced celery, diced onion, salt, and pepper until well combined.#Spread the tuna salad onto a slice of bread.#Top with lettuce and another slice of bread.#Cut the sandwich in half and serve.", R.drawable.tuna_salad_sandwich,5);
        recipesRef.child(recipe13.getId()).setValue(recipe13);

        List<String> ingredients14 = Arrays.asList("sliced bread", "bacon", "lettuce", "tomato", "mayonnaise");
        Recipe recipe14 = new Recipe("14", "Classic BLT Sandwich", "Dinner", ingredients14, "savory", false, 400, 20, "Cook bacon in a skillet over medium heat until crispy. Set aside on paper towels to drain.#Spread mayonnaise on one side of each slice of bread.#Layer bacon, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.classic_blt_sandwich,4);
        recipesRef.child(recipe14.getId()).setValue(recipe14);


        List<String> ingredients = Arrays.asList("sliced bread", "bacon", "lettuce", "tomato", "mayonnaise");

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

