package com.example.spectacleapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    Button btnSpectacles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        btnSpectacles = findViewById(R.id.btnSpectacles);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView btnCreateAccount = findViewById(R.id.btnCreateAccount);

        Button btnSpectacles = findViewById(R.id.btnSpectacles);
        btnSpectacles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SpectaclesActivity.class);
            startActivity(intent);
        });


        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class); // rename AdminLoginActivity to LoginActivity
            startActivity(intent);
        });

        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }
}
