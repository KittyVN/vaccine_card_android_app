package com.example.MOCO;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

public class VaccineActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private String[] vaccineKrankheit = new String[3];
    private String[] vaccineHersteller = new String[3];
    private String[] vaccineDate = new String[3];
    private RecyclerView rvVaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavVaccine);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.bottomNavVaccine:
                        return true;
                    case R.id.bottomNavTiter:
                        startActivity(new Intent(getApplicationContext(),TiterActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomNavHome:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomNavCountry:
                        startActivity(new Intent(getApplicationContext(),CountryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        vaccineKrankheit[0] = "test1";
        vaccineKrankheit[1] = "test1";
        vaccineKrankheit[2] = "test1";
        vaccineHersteller[0] = "hersteller1";
        vaccineHersteller[1] = "hersteller2";
        vaccineHersteller[2] = "hersteller3";
        vaccineDate[0] = "2022/01/01";
        vaccineDate[1] = "2022/01/01";
        vaccineDate[2] = "2022/01/01";

        rvVaccine = findViewById(R.id.rvVaccine);

        VaccineAdapter vacAdapter = new VaccineAdapter(this,vaccineKrankheit,vaccineHersteller,vaccineDate);
        rvVaccine.setAdapter(vacAdapter);
        rvVaccine.setLayoutManager(new LinearLayoutManager(this));

    }
}