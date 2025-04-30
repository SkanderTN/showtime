package com.example.spectacleapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SpectaclesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> spectacleList;
    private List<Spectacle> filteredList;

    private Spinner spinnerCity;
    private EditText editDate;
    private Button btnSearch;

    private final String[] cities = {
            "All Cities", "Tunis", "Sousse", "Monastir", "Mahdia", "Nabeul", "Bizerte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacles);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Profile icon logic
        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String email = prefs.getString("email", "");

            if (email.isEmpty()) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }); // <-- ❗❗ THIS was missing

        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerSpectacles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerCity = findViewById(R.id.spinnerCity);
        editDate = findViewById(R.id.editDateSearch);
        btnSearch = findViewById(R.id.btnSearch);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item_white, cities
        );
        cityAdapter.setDropDownViewResource(R.layout.spinner_item_white);
        spinnerCity.setAdapter(cityAdapter);

        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> openDatePicker());

        spectacleList = new ArrayList<>();
        loadStaticData();

        filteredList = new ArrayList<>(spectacleList);
        adapter = new SpectacleAdapter(filteredList, this);
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> filterSpectacles());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = 2025; // optional: default to your data's year
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String[] months = {"JAN", "FEB", "MAR", "AVR", "MAY", "JUN",
                            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
                    String formatted = String.format("%02d-%s-%02d", selectedDay, months[selectedMonth], selectedYear % 100);
                    editDate.setText(formatted);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void filterSpectacles() {
        String selectedCity = spinnerCity.getSelectedItem().toString().trim().toLowerCase();
        String dateQuery = editDate.getText().toString().trim().toLowerCase();

        filteredList.clear();

        for (Spectacle s : spectacleList) {
            String spectacleCity = extractCityFromLieu(s.lieu).toLowerCase();
            boolean matchesCity = selectedCity.equals("all cities") || spectacleCity.equals(selectedCity);
            boolean matchesDate = dateQuery.isEmpty() || s.date.toLowerCase().contains(dateQuery);

            if (matchesCity && matchesDate) {
                filteredList.add(s);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private String extractCityFromLieu(String lieu) {
        if (lieu.contains("-")) {
            return lieu.substring(lieu.indexOf("-") + 1).trim();
        }
        return lieu.trim();
    }

    private void loadStaticData() {
        spectacleList.add(new Spectacle("La Nuit des Stars", "25-AVR-25", "20:00", "Palais Lumière - Tunis", "2h 30min", R.drawable.show1));
        spectacleList.add(new Spectacle("La Nuit des Stars", "25-AVR-26", "20:00", "Palais Lumière - Sousse", "2h 30min", R.drawable.show1));
        spectacleList.add(new Spectacle("La Nuit des Stars", "25-AVR-25", "20:00", "Palais Lumière - Tunis", "2h 30min", R.drawable.show1));

        spectacleList.add(new Spectacle("Soirée Humoristique", "10-MAY-25", "20:15", "Salle RireMax - Sousse", "1h 30min", R.drawable.show2));
        spectacleList.add(new Spectacle("Danse du Monde", "20-MAY-25", "20:30", "Espace Culture - Monastir", "1h 30min", R.drawable.show3));
        spectacleList.add(new Spectacle("Opéra de la Mer", "12-JUN-25", "21:00", "Théâtre Bleu - Mahdia", "2h", R.drawable.show1));
        spectacleList.add(new Spectacle("Comédie Blanche", "05-JUL-25", "19:00", "Théâtre Central - Tunis", "1h 45min", R.drawable.show2));
        spectacleList.add(new Spectacle("Festival de Danse", "22-JUL-25", "18:00", "Centre Culturel - Sousse", "3h", R.drawable.show3));
        spectacleList.add(new Spectacle("Nuit Amazigh", "03-AUG-25", "20:30", "Salle Imazighen - Nabeul", "2h", R.drawable.show1));
        spectacleList.add(new Spectacle("Jazz & Chill", "15-AUG-25", "21:00", "Lac Center - Tunis", "1h 30min", R.drawable.show2));
        spectacleList.add(new Spectacle("Spectacle Magique", "28-AUG-25", "20:00", "Espace Jeunesse - Bizerte", "2h 15min", R.drawable.show3));


        AllSpectaclesListHolder.setSpectacles(spectacleList);

    }
}
