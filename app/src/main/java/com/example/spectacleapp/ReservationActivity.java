package com.example.spectacleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    private int ticketPrice = 25;
    private int availableSeats = 20;
    private int selectedTickets = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reserve Tickets");

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

        TextView tvTitle = findViewById(R.id.tvSpectacleTitle);
        ImageView imgSpectacle = findViewById(R.id.imgSpectacle);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvAvailable = findViewById(R.id.tvAvailable);
        TextView tvTotal = findViewById(R.id.tvTotal);
        Spinner spinnerTickets = findViewById(R.id.spinnerTickets);
        Button btnConfirm = findViewById(R.id.btnConfirmReservation);

        // New: show date/time/lieu
        TextView tvDetails = findViewById(R.id.tvSpectacleDetails);

        // Get data from intent
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        int imageRes = intent.getIntExtra("imageRes", R.drawable.show1);
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String lieu = intent.getStringExtra("lieu");

        tvTitle.setText(titre);
        imgSpectacle.setImageResource(imageRes);
        tvPrice.setText("💵 Price: " + ticketPrice + " DT");
        tvAvailable.setText("🪑 Available Seats: " + availableSeats);
        tvTotal.setText("🧾 Total: " + ticketPrice + " DT");

        if (date != null && time != null && lieu != null) {
            tvDetails.setVisibility(View.VISIBLE);
            tvDetails.setText("📅 " + date + "  🕒 " + time + "\n🏛️ " + lieu);
        } else {
            tvDetails.setVisibility(View.GONE);
        }

        // Spinner setup
        List<String> ticketOptions = new ArrayList<>();
        for (int i = 1; i <= Math.min(availableSeats, 10); i++) {
            ticketOptions.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, ticketOptions);
        adapter.setDropDownViewResource(R.layout.spinner_item_white);
        spinnerTickets.setAdapter(adapter);

        spinnerTickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTickets = Integer.parseInt(ticketOptions.get(position));
                int total = selectedTickets * ticketPrice;
                tvTotal.setText("🧾 Total: " + total + " DT");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTickets = 1;
            }
        });

        btnConfirm.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String email = prefs.getString("email", "");

            if (email.isEmpty()) {
                Intent formIntent = new Intent(this, ReservationFormActivity.class);
                formIntent.putExtra("titre", titre);
                formIntent.putExtra("date", date);
                formIntent.putExtra("time", time);
                formIntent.putExtra("lieu", lieu);
                formIntent.putExtra("imageRes", imageRes);
                startActivity(formIntent);
            } else {
                Intent thankIntent = new Intent(this, ThankYouActivity.class);
                thankIntent.putExtra("titre", titre);
                thankIntent.putExtra("date", date);
                thankIntent.putExtra("time", time);
                thankIntent.putExtra("lieu", lieu);
                thankIntent.putExtra("imageRes", imageRes);
                startActivity(thankIntent);
            }
        });
    }
}
