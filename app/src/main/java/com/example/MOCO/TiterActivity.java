package com.example.MOCO;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class TiterActivity extends AppCompatActivity {
    BottomNavigationView btmNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titer);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavTiter);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.bottomNavVaccine:
                        startActivity(new Intent(getApplicationContext(),VaccineActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomNavTiter:
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
    }
}