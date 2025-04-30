package com.example.spectacleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReservationFormActivity extends AppCompatActivity {

    private EditText editFullName, editEmail;
    private Button btnConfirmReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> finish());

        // Fields
        editFullName = findViewById(R.id.editFullName);
        editEmail = findViewById(R.id.editEmail);
        btnConfirmReservation = findViewById(R.id.btnConfirmReservation);

        btnConfirmReservation.setOnClickListener(v -> {
            String fullName = editFullName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields ❗", Toast.LENGTH_SHORT).show();
            } else {
                // Save login temporarily (simulate login)
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("fullName", fullName);
                editor.putString("email", email);
                editor.apply();

                // Go to Thank You page
                Intent intent = new Intent(this, ThankYouActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
