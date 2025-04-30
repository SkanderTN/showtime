package com.example.spectacleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpectacleDetailsActivity extends AppCompatActivity {

    private List<Spectacle> allOtherSessions;
    private SpectacleSessionAdapter sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_details);

        // Setup Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


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

        TextView title = findViewById(R.id.tvDetailsTitle);
        TextView desc = findViewById(R.id.tvDescription);
        TextView extraDetails = findViewById(R.id.tvExtraDetails);
        TextView place = findViewById(R.id.tvDetailsLieu);
        ImageView image = findViewById(R.id.imgDetailsPoster);
        Button btnReserve = findViewById(R.id.btnReserveNow);

        Intent i = getIntent();
        String titre = i.getStringExtra("titre");
        String lieu = i.getStringExtra("lieu");
        String date = i.getStringExtra("date");
        String time = i.getStringExtra("time");
        String descTxt = i.getStringExtra("desc");
        int imgRes = i.getIntExtra("imageRes", R.drawable.show1);

        title.setText(titre);
        desc.setText(descTxt);
        extraDetails.setText("📅 " + date + "   🕒 " + time + "\n🏛️ " + lieu);
        place.setText("📍 " + lieu);
        image.setImageResource(imgRes);

        place.setOnClickListener(v -> {
            String query = Uri.encode(lieu);
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        btnReserve.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReservationActivity.class);
            intent.putExtra("titre", titre);
            intent.putExtra("imageRes", imgRes);
            startActivity(intent);
        });

        // RecyclerView + Filter
        TextView tvOtherSessions = findViewById(R.id.tvOtherSessions);
        RecyclerView recyclerOtherSessions = findViewById(R.id.recyclerOtherSessions);
        Spinner spinnerCityFilter = findViewById(R.id.spinnerCityFilter);

        allOtherSessions = new ArrayList<>();

        if (AllSpectaclesListHolder.getSpectacles() != null) {
            for (Spectacle s : AllSpectaclesListHolder.getSpectacles()) {
                if (s.titre.equals(titre) && !s.lieu.equals(lieu)) {
                    allOtherSessions.add(s);
                }
            }
        }

        if (!allOtherSessions.isEmpty()) {
            tvOtherSessions.setVisibility(View.VISIBLE);
            recyclerOtherSessions.setVisibility(View.VISIBLE);
            spinnerCityFilter.setVisibility(View.VISIBLE);

            recyclerOtherSessions.setLayoutManager(new LinearLayoutManager(this));
            sessionAdapter = new SpectacleSessionAdapter(allOtherSessions, this);
            recyclerOtherSessions.setAdapter(sessionAdapter);

            // Setup City Spinner
            setupCityFilter(spinnerCityFilter);
        }
    }

    private void setupCityFilter(Spinner spinner) {
        List<String> cities = new ArrayList<>();
        cities.add("All Cities");

        for (Spectacle s : allOtherSessions) {
            String city = extractCityFromLieu(s.lieu);
            if (!cities.contains(city)) {
                cities.add(city);
            }
        }

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, cities);
        cityAdapter.setDropDownViewResource(R.layout.spinner_item_white);
        spinner.setAdapter(cityAdapter);

        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = cities.get(position);
                filterSessionsByCity(selectedCity);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void filterSessionsByCity(String city) {
        List<Spectacle> filtered = new ArrayList<>();

        if (city.equals("All Cities")) {
            filtered.addAll(allOtherSessions);
        } else {
            for (Spectacle s : allOtherSessions) {
                if (extractCityFromLieu(s.lieu).equalsIgnoreCase(city)) {
                    filtered.add(s);
                }
            }
        }

        sessionAdapter.updateList(filtered);
    }

    private String extractCityFromLieu(String lieu) {
        if (lieu.contains("-")) {
            String[] parts = lieu.split("-");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }
        return lieu;
    }
}
