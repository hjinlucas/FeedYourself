package com.example.feedyourself.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.feedyourself.R;
import com.example.feedyourself.adapters.Recipe;
import com.example.feedyourself.fragments.MainPageFragment;
import com.example.feedyourself.fragments.SavedFragment;
import com.example.feedyourself.fragments.SearchFragment;
import com.example.feedyourself.fragments.RegisterFragment;
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
//        List<String> ingredients1 = Arrays.asList("Beef", "Potato");
//        Recipe recipe1 = new Recipe("3", "Stewed Beef Brisket with Potatoes", "Dinner", ingredients1, "Sweet",false,0,0,"",0);

//        List<String> ingredients2 = Arrays.asList("Ingredient4", "Ingredient5", "Ingredient6");
//        Recipe recipe2 = new Recipe("2", "Recipe Name 2", "Lunch", ingredients2, "Spicy");

        // Store the recipes in Firebase
//        recipesRef.child(recipe1.getId()).setValue(recipe1);
//        recipesRef.child(recipe2.getId()).setValue(recipe2);

//      STYLES TO ADD RECIPES, NEWEST VERSION
//        List<String> ingredients2 = Arrays.asList("onion","milk","tomatoes","cheddar cheese","Sausage");
//        Recipe recipe2 = new Recipe("2","Sausage and Cheese Pie","Breakfast",ingredients2,"",false,500, 90,"Preheat oven to 400°F.#Cook sausage and onion in a large saucepan over medium-high heat 8 to 10 minutes or until thoroughly cooked, stirring frequently; drain. Stir in salt and pepper. Spoon sausage mixture into a lightly greased, 9-inch glass pie plate.#Combine milk, baking mix and eggs in a medium bowl and blend with a wire whisk. Pour batter over sausage in pie plate.#Bake 25 minutes or until knife inserted in center comes out clean. Remove from oven. Top with sliced tomato and sprinkle with cheese. Bake an additional 5 minutes or until cheese is melted.#Let stand minutes before serving.", R.drawable.cheese_pie);
//        recipesRef.child(recipe2.getId()).setValue(recipe2);
//
//        List<String> ingredients3 = Arrays.asList("Smoked Bacon","milk","French bread cubes","cheddar cheese");
//        Recipe recipe3 = new Recipe("1","Easy Bacon and Egg Casserole","Breakfast",ingredients3,"",false,400, 50,"Preheat oven to 350°F.#Place bacon slices in unheated skillet. Cook slowly over LOW heat, turning occasionally and draining excess drippings as needed. Crumble and set aside.#Beat eggs, milk and pepper in large bowl with a wire whisk until well blended. Add bread cubes; stir gently until evenly coated. Stir in bacon crumbles and 1 ½ cups cheese.#Pour into lightly greased 13x9-inch baking dish; sprinkle with remaining cheese.#Bake 45 minutes or until knife inserted in center comes out clean. Let stand 10 minutes before cutting into 12 squares to serve.",R.drawable.bacon_egg_casserole);
//        recipesRef.child(recipe3.getId()).setValue(recipe3);


        List<String> ingredients11 = Arrays.asList("sliced bread", "turkey", "cheddar cheese", "lettuce", "tomato", "mayonnaise");
        Recipe recipe11 = new Recipe("10", "Turkey and Cheese Sandwich", "Lunch", ingredients11, "", false, 300, 10, "#Spread mayonnaise on one side of each slice of bread.#Layer turkey, cheddar cheese, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe11.getId()).setValue(recipe11);

        List<String> ingredients12 = Arrays.asList("sliced bread", "ham", "swiss cheese", "butter", "dijon mustard");
        Recipe recipe12 = new Recipe("11", "Ham and Cheese Panini", "Lunch", ingredients12, "", false, 400, 15, "#Spread butter on one side of each slice of bread.#On the other side of one slice, spread dijon mustard.#Layer ham and swiss cheese on top of the mustard.#Place the second slice of bread on top, butter-side out.#Heat a skillet or panini press over medium heat.#Cook the sandwich for 3-4 minutes on each side or until bread is golden brown and cheese is melted.#Serve immediately.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe12.getId()).setValue(recipe12);

        List<String> ingredients13 = Arrays.asList("canned tuna", "mayonnaise", "diced celery", "diced onion", "salt", "pepper", "sliced bread", "lettuce");
        Recipe recipe13 = new Recipe("12", "Tuna Salad Sandwich", "Lunch", ingredients13, "", false, 400, 15, "#In a medium bowl, mix together tuna, mayonnaise, diced celery, diced onion, salt, and pepper until well combined.#Spread the tuna salad onto a slice of bread.#Top with lettuce and another slice of bread.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe13.getId()).setValue(recipe13);

        List<String> ingredients14 = Arrays.asList("sliced bread", "bacon", "lettuce", "tomato", "mayonnaise");
        Recipe recipe14 = new Recipe("13", "Classic BLT Sandwich", "Lunch", ingredients14, "", false, 400, 20, "#Cook bacon in a skillet over medium heat until crispy. Set aside on paper towels to drain.#Spread mayonnaise on one side of each slice of bread.#Layer bacon, lettuce, and tomato on one slice of bread.#Top with the second slice of bread, mayonnaise-side down.#Cut the sandwich in half and serve.", R.drawable.bacon_egg_casserole);
        recipesRef.child(recipe14.getId()).setValue(recipe14);
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

