package com.example.MOCO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Location;

import java.lang.ref.WeakReference;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private Context ctx = this;
    private CountryActivity activity = this;
    //private TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputCountry);
    private List<String> countryName = new ArrayList<String>();
    private List<String> countryNecessary = new ArrayList<String>();
    private List<String> countryRecommended = new ArrayList<String>();
    private RecyclerView rvCountry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavCountry);
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
                        startActivity(new Intent(getApplicationContext(),TiterActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomNavHome:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomNavCountry:
                        return true;
                }
                return false;
            }
        });


        ImageButton btnCountrySearch = (ImageButton) findViewById(R.id.btnSearch);
        btnCountrySearch.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new CountryTask(activity).execute();
            }
        });
    }

    private static class CountryTask extends AsyncTask<Void, Object, List<Location>> {

        private WeakReference<CountryActivity> activityReference;

        // only retain a weak reference to the activity
        CountryTask(CountryActivity context) {
            activityReference = new WeakReference<>(context);
        }
        
        @Override
        protected List<Location> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();

            //fix so it shows the country you searched for
            List<Location> listCountries = gcm.getLocations("");

            return listCountries;
        }
        @Override
        protected void onPostExecute(List<Location> locations) {
            CountryActivity activity = activityReference.get();

            for (Location location : locations) {
                activity.countryName.add(location.getName());
                activity.countryNecessary.add("test");
                activity.countryRecommended.add("test");
            }

            activity.rvCountry = activity.findViewById(R.id.rvCountry);

            CountryAdapter countryAdapter = new CountryAdapter(activity.ctx, activity.countryName, activity.countryNecessary, activity.countryRecommended);
            activity.rvCountry.setAdapter(countryAdapter);
            activity.rvCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));
        }
    }
}