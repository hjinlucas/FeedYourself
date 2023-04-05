package com.example.feedyourself.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.feedyourself.R;
import com.example.feedyourself.Recipe;
import com.example.feedyourself.saved.SavedFragment;
import com.example.feedyourself.search.SearchFragment;
import com.example.feedyourself.user.RegisterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private FloatingActionButton confirmationButton;

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
        List<String> ingredients1 = Arrays.asList("Beef", "Potato");
        Recipe recipe1 = new Recipe("3", "Stewed Beef Brisket with Potatoes", "Dinner", ingredients1, "Sweet");

//        List<String> ingredients2 = Arrays.asList("Ingredient4", "Ingredient5", "Ingredient6");
//        Recipe recipe2 = new Recipe("2", "Recipe Name 2", "Lunch", ingredients2, "Spicy");

        // Store the recipes in Firebase
        recipesRef.child(recipe1.getId()).setValue(recipe1);
//        recipesRef.child(recipe2.getId()).setValue(recipe2);
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




}

