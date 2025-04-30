package com.example.spectacleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String email = prefs.getString("email", "");

            if (email.isEmpty()) {
                // Not logged in → Go to Login Page
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                // Logged in → Go to Profile Page
                startActivity(new Intent(this, ProfileActivity.class));
            }
        });
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvFullName = findViewById(R.id.tvFullName);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvHistory = findViewById(R.id.tvReservationHistory);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = prefs.getString("fullName", "Guest");
        String email = prefs.getString("email", "No Email");

        tvFullName.setText("👤 " + fullName);
        tvEmail.setText("📧 " + email);
        tvHistory.setText("🧾 Reservation History:\n(No records yet)");
    }
}
