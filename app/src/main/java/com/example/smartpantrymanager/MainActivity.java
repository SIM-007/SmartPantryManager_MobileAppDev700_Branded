package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private PantryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Smart Pantry Manager");

        db = new DatabaseHelper(this);
        RecyclerView recycler = findViewById(R.id.recyclerPantry);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PantryAdapter(db.getAllIngredients(), new PantryAdapter.Listener() {
            @Override
            public void edit(Ingredient ingredient) {
                Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
                intent.putExtra("id", ingredient.getId());
                startActivity(intent);
            }

            @Override
            public void delete(Ingredient ingredient) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete ingredient")
                        .setMessage("Delete " + ingredient.getName() + " from your pantry?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete", (dialog, which) -> {
                            db.deleteIngredient(ingredient.getId());
                            loadPantry();
                            Toast.makeText(MainActivity.this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });
        recycler.setAdapter(adapter);

        Button addButton = findViewById(R.id.btnAdd);
        Button suggestionsButton = findViewById(R.id.btnSuggestions);
        Button settingsButton = findViewById(R.id.btnSettings);

        addButton.setOnClickListener(v -> openAddIngredient());
        suggestionsButton.setOnClickListener(v -> openSuggestions());
        settingsButton.setOnClickListener(v -> openSettings());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db != null && adapter != null) {
            loadPantry();
        }
    }

    private void loadPantry() {
        adapter.setItems(db.getAllIngredients());
    }

    private void openAddIngredient() {
        startActivity(new Intent(this, AddEditIngredientActivity.class));
    }

    private void openSuggestions() {
        startActivity(new Intent(this, SuggestedRecipesActivity.class));
    }

    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            openAddIngredient();
            return true;
        } else if (id == R.id.menu_suggestions) {
            openSuggestions();
            return true;
        } else if (id == R.id.menu_settings) {
            openSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
