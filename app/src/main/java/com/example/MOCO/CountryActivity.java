package com.example.MOCO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.ImmunizationRecommendation;
import org.hl7.fhir.r4.model.Location;

import java.lang.ref.WeakReference;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private Context ctx = this;
    private CountryActivity activity = this;
    private List<String> countryName = new ArrayList<String>();
    private ArrayList<ArrayList<String>> countryNecessary = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> countryRecommended = new ArrayList<ArrayList<String>>();
    private RecyclerView rvNecessaryCountry;
    private RecyclerView rvRecommendedCountry;
    private TextView tvCountryName;
    private String enteredSearchCountry;
    private TextView tvRecommendedHint;
    private TextView tvNecessaryHint;
    private ImageView ivGlobe;
    private CardView cvNecessary;
    private CardView cvRecommended;


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
        TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputCountry);

        btnCountrySearch.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enteredSearchCountry = tvSearch.getText().toString();
                new CountryTask(activity).execute();
            }
        });
    }

    private static class CountryTask extends AsyncTask<Void, Object, List<ImmunizationRecommendation>> {

        private WeakReference<CountryActivity> activityReference;
        private CountryActivity activity;

        // only retain a weak reference to the activity
        CountryTask(CountryActivity context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }
        
        @Override
        protected List<ImmunizationRecommendation> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();

            //List<Location> listCountries = gcm.getLocations(activity.enteredSearchCountry);
            List<ImmunizationRecommendation> listRecommendations = gcm.getRecommendation(activity.enteredSearchCountry);

            return listRecommendations;
        }
        @Override
        protected void onPostExecute(List<ImmunizationRecommendation> recommendations) {

            activity.countryName.clear();
            activity.countryNecessary.clear();
            activity.countryRecommended.clear();


            for (ImmunizationRecommendation recommendation : recommendations) {

                if (recommendation.getExtension().size() > 0){
                    activity.countryName.add(recommendation.getExtension().get(0).getValue().toString());
                }
                ArrayList<String> temp = new ArrayList<>();
                ArrayList<String> temp1 = new ArrayList<>();

                for (int i = 0; i < recommendation.getRecommendation().size(); i++){
                    if (recommendation.getRecommendation().get(i).hasDescription()){
                        temp.add(recommendation.getRecommendation().get(i).getTargetDisease().getCoding().get(0).getDisplay() + " - " + recommendation.getRecommendation().get(i).getDescription().toString());
                        activity.countryRecommended.add(temp);
                    }else if (!recommendation.getRecommendation().get(i).hasDescription()){
                        temp1.add(recommendation.getRecommendation().get(i).getTargetDisease().getCoding().get(0).getDisplay());
                        activity.countryNecessary.add(temp1);
                    }
                }
            }
            CountryNecessaryAdapter NAdapter;
            CountryRecommendedAdapter RAdapter;

            //set visibility of of hints above recyclers
            activity.tvRecommendedHint = activity.findViewById(R.id.tvRecommended);
            activity.tvNecessaryHint = activity.findViewById(R.id.tvNescessary);
            activity.tvRecommendedHint.setVisibility(View.VISIBLE);
            activity.tvNecessaryHint.setVisibility(View.VISIBLE);

            activity.cvNecessary = activity.findViewById(R.id.cvNecessary);
            activity.cvRecommended = activity.findViewById(R.id.cvRecommended);

            activity.cvNecessary.setVisibility(View.VISIBLE);
            activity.cvRecommended.setVisibility(View.VISIBLE);


            activity.ivGlobe = activity.findViewById(R.id.ivGlobe);
            activity.ivGlobe.setVisibility(View.INVISIBLE);

            NAdapter = new CountryNecessaryAdapter(activity.ctx, activity.countryNecessary.get(0));
            RAdapter = new CountryRecommendedAdapter(activity.ctx, activity.countryRecommended.get(0));

            activity.tvCountryName= activity.findViewById(R.id.tvCountryName);
            activity.tvCountryName.setText(activity.countryName.get(0));

            System.out.println("testout");

            activity.rvNecessaryCountry = activity.findViewById(R.id.rvNecessaryCountry);
            activity.rvRecommendedCountry = activity.findViewById(R.id.rvRecommendedCountry);

            activity.rvNecessaryCountry.setAdapter(NAdapter);
            activity.rvNecessaryCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));

            activity.rvRecommendedCountry.setAdapter(RAdapter);
            activity.rvRecommendedCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));


        }
    }
/*
    private static class CountryTask1 extends AsyncTask<Void, Object, List<String>> {
        private WeakReference<CountryActivity> activityReference;


        CountryTask1(CountryActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();
            CountryActivity activity = activityReference.get();


            //return list;
            for(int i =0 ; i < activity.countryName.size(); i++) {
                if (activity.countryName.get(i) != null){
                    //activity.countryName.set(i,gcm.getLocation(activity.countryName.get(i)).getName());
                }
            }

            return activity.countryName;
        }

        @Override
        protected void onPostExecute(List<String> countryList) {
            CountryActivity activity = activityReference.get();
            CountryNecessaryAdapter NAdapter = null;
            CountryRecommendedAdapter RAdapter = null;

            for(int i = 0; i < activity.countryName.size(); i++) {
                if(activity.countryName.get(i) == activity.enteredSearchCountry) {
                    NAdapter = new CountryNecessaryAdapter(activity.ctx, activity.countryNecessary.get(i));
                    RAdapter = new CountryRecommendedAdapter(activity.ctx, activity.countryRecommended.get(i));

                    activity.tvCountryName= activity.findViewById(R.id.tvCountryName);
                    activity.tvCountryName.setText(activity.countryName.get(i));

                    System.out.println("testout");

                    activity.rvNecessaryCountry = activity.findViewById(R.id.rvNecessaryCountry);
                    activity.rvRecommendedCountry = activity.findViewById(R.id.rvRecommendedCountry);

                    activity.rvNecessaryCountry.setAdapter(NAdapter);
                    activity.rvNecessaryCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));

                    activity.rvRecommendedCountry.setAdapter(RAdapter);
                    activity.rvRecommendedCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));
                }
            }


        }
    }
*/
}
