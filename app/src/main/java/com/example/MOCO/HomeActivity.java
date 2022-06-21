package com.example.MOCO;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavHome);
        btmNavView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.bottomNavVaccine:
                    startActivity(new Intent(getApplicationContext(), VaccineActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.bottomNavTiter:
                    startActivity(new Intent(getApplicationContext(), TiterActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.bottomNavHome:
                    return true;
                case R.id.bottomNavCountry:
                    startActivity(new Intent(getApplicationContext(), CountryActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        AppCompatButton btnVaccine = (AppCompatButton) findViewById(R.id.btnHome_Impfungen);
        btnVaccine.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, VaccineActivity.class)));

        AppCompatButton btnTiter = (AppCompatButton) findViewById(R.id.btnHome_Titer);
        btnTiter.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TiterActivity.class)));

        AppCompatButton btnCountry = (AppCompatButton) findViewById(R.id.btnHome_Reisen);
        btnCountry.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CountryActivity.class)));

    }
}
