package com.example.feedyourself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeResultsActivity extends AppCompatActivity {

    private RecyclerView recipeResultsRecyclerView;
    private TextView filterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_results);

        recipeResultsRecyclerView = findViewById(R.id.recipeResultsRecyclerView);
        filterTextView = findViewById(R.id.filterTextView);

        List<String> recipeDataList = new ArrayList<>(); // Replace String with your actual recipe data model class
        // Add recipe data to the list, e.g., recipeDataList.add("Recipe 1");
        recipeDataList.add("Recipe 1");

        // Set up the RecyclerView with a GridLayoutManager and your custom adapter
        recipeResultsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        RecipeResultsAdapter adapter = new RecipeResultsAdapter(this, recipeDataList);
        recipeResultsRecyclerView.setAdapter(adapter);

        filterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterPopupMenu(view);
            }
        });
    }

    private void showFilterPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item clicks here
                switch (item.getItemId()) {
                    case R.id.popular:
                        // Apply popular filter
                        break;
                    case R.id.score:
                        // Apply score filter
                        break;
                    case R.id.latest:
                        // Apply latest filter
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}


