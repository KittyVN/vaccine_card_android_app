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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.ImmunizationRecommendation;

import java.lang.ref.WeakReference;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.parser.IParser;

public class VaccineActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private List<String> vaccineKrankheit = new ArrayList<String>();
    private List<String> vaccineHersteller = new ArrayList<String>();
    private List<String> vaccineDate = new ArrayList<String>();
    private List<String> vaccineLotNumber = new ArrayList<String>();
    private List<String> vaccinePerformer = new ArrayList<String>();
    private List<String> vaccineDoseNumber = new ArrayList<String>();
    private List<String> vaccineDoseNumberTotal = new ArrayList<String>();


    private RecyclerView rvVaccine;
    private Context ctx = this;
    private VaccineActivity activity = this;
    private String enteredSearchTarget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavVaccine);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.bottomNavVaccine:
                        return true;
                    case R.id.bottomNavTiter:
                        startActivity(new Intent(getApplicationContext(), TiterActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.bottomNavHome:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.bottomNavCountry:
                        startActivity(new Intent(getApplicationContext(), CountryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputVaccine);
        ImageButton btnCountrySearch = (ImageButton) findViewById(R.id.btnSearch1);
        new VaccineTask(activity).execute();


        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                    enteredSearchTarget = tvSearch.getText().toString();
                    tvSearch.setText("");
                    new VaccineTask(activity).execute();

                }
                return handled;
            }
        });


        btnCountrySearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredSearchTarget = tvSearch.getText().toString();
                tvSearch.setText("");
                new VaccineTask(activity).execute();
            }
        });

    }

    private static class VaccineTask extends AsyncTask<Void, Object, List<Immunization>> {

        private WeakReference<VaccineActivity> activityReference;
        private VaccineActivity activity;


        // only retain a weak reference to the activity
        VaccineTask(VaccineActivity context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }
        @Override
        protected List<Immunization> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();
            List<Immunization> listVaccines = new ArrayList<>();

            if (activity.enteredSearchTarget == "" || activity.enteredSearchTarget == null){
                listVaccines = gcm.getAllVacciness();
            }else {
                listVaccines = gcm.getVacciness(activity.enteredSearchTarget);
            }

            return listVaccines;
        }
        @Override
        protected void onPostExecute(List<Immunization> vaccines) {
            VaccineActivity activity = activityReference.get();

            activity.vaccinePerformer.clear();
            activity.vaccineKrankheit.clear();
            activity.vaccineDoseNumberTotal.clear();
            activity.vaccineDate.clear();
            activity.vaccineDoseNumber.clear();
            activity.vaccineHersteller.clear();
            activity.vaccineLotNumber.clear();

            if (vaccines.size() != 0) {
                for (Immunization vaccine : vaccines) {
                    activity.vaccineKrankheit.add(vaccine.getVaccineCode().getCoding().get(0).getDisplay());
                    activity.vaccineHersteller.add(vaccine.getManufacturer().getDisplay());
                    activity.vaccineDate.add(vaccine.getOccurrenceDateTimeType().asStringValue());
                    activity.vaccineLotNumber.add(vaccine.getLotNumber());
                    if (vaccine.getPerformer().size() > 0) {
                        activity.vaccinePerformer.add(vaccine.getPerformer().get(0).getActor().getDisplay());
                    } else {
                        activity.vaccinePerformer.add("Unknown");
                    }
                    if (vaccine.getProtocolApplied().size() > 0) {
                        activity.vaccineDoseNumber.add(vaccine.getProtocolApplied().get(0).getDoseNumberPositiveIntType().asStringValue());
                        activity.vaccineDoseNumberTotal.add(vaccine.getProtocolApplied().get(0).getSeriesDosesPositiveIntType().asStringValue());
                    } else {
                        activity.vaccineDoseNumber.add("Unknown");
                        activity.vaccineDoseNumberTotal.add("Unknown");
                    }
                }
                System.out.println(activity.vaccineKrankheit);

                //get the references
                //new VaccineTask1(activity).execute();

                activity.rvVaccine = activity.findViewById(R.id.rvVaccine);

                VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.vaccineKrankheit, activity.vaccineHersteller, activity.vaccineDate, activity.vaccineLotNumber, activity.vaccinePerformer, activity.vaccineDoseNumber, activity.vaccineDoseNumberTotal);
                activity.rvVaccine.setAdapter(vacAdapter);
                activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
            }
        }
    }
/*
    private static class VaccineTask1 extends AsyncTask<Void, Object, List<String>> {
        private WeakReference<VaccineActivity> activityReference;



        VaccineTask1(VaccineActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();
            VaccineActivity activity = activityReference.get();


            //return list;
            for(int i =0 ; i < activity.vaccineHersteller.size(); i++) {
                if (activity.vaccineHersteller.get(i) != null){
                    activity.vaccineHersteller.set(i,gcm.getOrganization(activity.vaccineHersteller.get(i)).getName());
                }
            }

            return activity.vaccineHersteller;
        }

        @Override
        protected void onPostExecute(List<String> herstellerList) {
            VaccineActivity activity = activityReference.get();

            for (String hersteller : herstellerList) {
                System.out.println(hersteller);
            }

            activity.rvVaccine = activity.findViewById(R.id.rvVaccine);

            VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.vaccineKrankheit, activity.vaccineHersteller, activity.vaccineDate, activity.vaccineLotNumber, activity.vaccinePerformer,activity.vaccineDoseNumber,activity.vaccineDoseNumberTotal);
            activity.rvVaccine.setAdapter(vacAdapter);
            activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
        }
    }*/
}
