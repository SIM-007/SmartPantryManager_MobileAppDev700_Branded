package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText name;
    private EditText quantity;
    private EditText unit;
    private EditText expiry;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        db = new DatabaseHelper(this);
        name = findViewById(R.id.edtName);
        quantity = findViewById(R.id.edtQuantity);
        unit = findViewById(R.id.edtUnit);
        expiry = findViewById(R.id.edtExpiry);

        id = getIntent().getIntExtra("id", -1);
        setTitle(id == -1 ? "Add Ingredient" : "Edit Ingredient");

        if (id != -1) {
            Ingredient ingredient = db.getIngredient(id);
            if (ingredient != null) {
                name.setText(ingredient.getName());
                quantity.setText(String.valueOf(ingredient.getQuantity()));
                unit.setText(ingredient.getUnit());
                expiry.setText(ingredient.getExpiryDate());
            }
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }

    private boolean validExpiry(String value) {
        if (TextUtils.isEmpty(value)) return true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        format.setLenient(false);
        try {
            Date parsed = format.parse(value);
            return parsed != null;
        } catch (ParseException e) {
            return false;
        }
    }

    private void save() {
        String n = name.getText().toString().trim();
        String q = quantity.getText().toString().trim();
        String u = unit.getText().toString().trim().toLowerCase(Locale.ROOT);
        String e = expiry.getText().toString().trim();

        name.setError(null);
        quantity.setError(null);
        unit.setError(null);
        expiry.setError(null);

        if (TextUtils.isEmpty(n)) {
            name.setError("Ingredient name is required");
            name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(q)) {
            quantity.setError("Quantity is required");
            quantity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(u)) {
            unit.setError("Unit is required");
            unit.requestFocus();
            return;
        }
        if (!isSupportedUnit(u)) {
            unit.setError("Use g, kg, ml, l, pieces or can");
            unit.requestFocus();
            return;
        }
        if (!validExpiry(e)) {
            expiry.setError("Use YYYY-MM-DD");
            expiry.requestFocus();
            return;
        }

        try {
            double value = Double.parseDouble(q);
            if (value <= 0) throw new NumberFormatException();
            if (id == -1) {
                db.insertIngredient(n, value, u, e);
            } else {
                db.updateIngredient(id, n, value, u, e);
            }
            Toast.makeText(this, id == -1 ? "Ingredient added" : "Ingredient updated", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException ex) {
            quantity.setError("Enter a quantity greater than zero");
            quantity.requestFocus();
        }
    }

    private boolean isSupportedUnit(String value) {
        return value.equals("g") || value.equals("kg") || value.equals("ml")
                || value.equals("l") || value.equals("piece") || value.equals("pieces")
                || value.equals("pc") || value.equals("pcs") || value.equals("can") || value.equals("cans");
    }
}
