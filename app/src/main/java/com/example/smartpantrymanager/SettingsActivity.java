package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        Switch showExpiry = findViewById(R.id.switchExpiry);
        showExpiry.setChecked(preferences.getBoolean("show_expiry", true));

        findViewById(R.id.btnSaveSettings).setOnClickListener(v -> {
            preferences.edit().putBoolean("show_expiry", showExpiry.isChecked()).apply();
            Toast.makeText(this, "Settings saved.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
