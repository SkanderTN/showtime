package com.example.spectacleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        toolbar.setNavigationOnClickListener(v -> finish());

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (isValidLogin(email, password)) {
                Toast.makeText(this, "Login successful ✅", Toast.LENGTH_SHORT).show();
                finish();  // Just close LoginActivity and return to MainActivity or Spectacles
            } else {
                Toast.makeText(this, "Invalid credentials ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidLogin(String email, String password) {
        // Later we will check from database
        // For now you can allow anything not empty
        return !email.isEmpty() && !password.isEmpty();
    }
}
