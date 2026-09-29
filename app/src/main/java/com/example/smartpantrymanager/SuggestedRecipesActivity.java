package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        setTitle("Suggested Recipes");
        db = new DatabaseHelper(this);
        empty = findViewById(R.id.txtNoRecipes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMatches();
    }

    private void showMatches() {
        RecyclerView recycler = findViewById(R.id.recyclerRecipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> matches = findStrictMatches();
        empty.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setAdapter(new RecipeAdapter(matches, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("name", recipe.getName());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("steps", recipe.getSteps());
            startActivity(intent);
        }));
    }

    /**
     * A recipe is suggested only when every required ingredient exists
     * and the pantry quantity is sufficient after unit conversion.
     */
    private List<Recipe> findStrictMatches() {
        Map<String, List<Ingredient>> pantry = new HashMap<>();
        for (Ingredient ingredient : db.getAllIngredients()) {
            pantry.computeIfAbsent(normalizeName(ingredient.getName()), k -> new ArrayList<>()).add(ingredient);
        }

        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : db.getRecipes()) {
            boolean qualifies = true;
            for (String requirement : recipe.getIngredients().split("\\|")) {
                String[] parts = requirement.split(":");
                if (parts.length != 3) {
                    qualifies = false;
                    break;
                }
                String requiredName = normalizeName(parts[0]);
                double requiredQuantity = Double.parseDouble(parts[1]);
                String requiredUnit = normalizeUnit(parts[2]);

                if (!hasEnough(pantry.get(requiredName), requiredQuantity, requiredUnit)) {
                    qualifies = false;
                    break;
                }
            }
            if (qualifies) matches.add(recipe);
        }
        return matches;
    }

    private boolean hasEnough(List<Ingredient> available, double required, String requiredUnit) {
        if (available == null || available.isEmpty()) return false;
        double total = 0;
        for (Ingredient ingredient : available) {
            Double converted = convertToRequiredUnit(ingredient.getQuantity(), normalizeUnit(ingredient.getUnit()), requiredUnit);
            if (converted != null) total += converted;
        }
        return total >= required;
    }

    private Double convertToRequiredUnit(double quantity, String from, String to) {
        if (from.equals(to)) return quantity;
        if (from.equals("kg") && to.equals("g")) return quantity * 1000;
        if (from.equals("g") && to.equals("kg")) return quantity / 1000;
        if (from.equals("l") && to.equals("ml")) return quantity * 1000;
        if (from.equals("ml") && to.equals("l")) return quantity / 1000;
        return null;
    }

    private String normalizeUnit(String unit) {
        String value = unit == null ? "" : unit.trim().toLowerCase(Locale.ROOT);
        if (value.equals("pc") || value.equals("pcs") || value.equals("piece")) return "pieces";
        if (value.equals("cans")) return "can";
        return value;
    }

    private String normalizeName(String name) {
        String value = name == null ? "" : name.trim().toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9 ]", "")
                .replaceAll("\\s+", " ");

        if (value.endsWith("ies") && value.length() > 3) return value.substring(0, value.length() - 3) + "y";
        if (value.endsWith("oes") && value.length() > 3) return value.substring(0, value.length() - 2);
        if (value.endsWith("es") && value.length() > 3) return value.substring(0, value.length() - 2);
        if (value.endsWith("s") && value.length() > 2) return value.substring(0, value.length() - 1);
        return value;
    }
}
