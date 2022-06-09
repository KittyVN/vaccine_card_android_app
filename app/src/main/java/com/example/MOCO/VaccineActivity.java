package com.example.MOCO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.hl7.fhir.r4.model.Immunization;

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

    private RecyclerView rvVaccine;
    private Context ctx = this;


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

        new VaccineTask(this).execute();

    }

    private static class VaccineTask extends AsyncTask<Void, Object, List<Immunization>> {

        private WeakReference<VaccineActivity> activityReference;

        // only retain a weak reference to the activity
        VaccineTask(VaccineActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected List<Immunization> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();
            //List<Patient> list = new ArrayList<Patient>();
            //list.add(gcm.getExamplePatient());

            //return list;
            List<Immunization> listVaccines = gcm.getVacciness();


            // IParser parser = gcm.ctx.newJsonParser();
            // for (Immunization vaccine : listVaccines) {
            //    Immunization imm = parser.parseResource(Immunization.class, listVaccines.get(0).toString());
            //}


            return listVaccines;
        }
        @Override
        protected void onPostExecute(List<Immunization> vaccines) {
            VaccineActivity activity = activityReference.get();

            for (Immunization vaccine : vaccines) {
                //b.append(vaccine.getText().getDiv().getValueAsString()).append('\n');
                activity.vaccineKrankheit.add(vaccine.getVaccineCode().getText());
                activity.vaccineHersteller.add(vaccine.getManufacturer().getReference());
                activity.vaccineDate.add(vaccine.getOccurrenceDateTimeType().asStringValue());
                activity.vaccineLotNumber.add(vaccine.getLotNumber());


                /*  Could be usefull if we want to use Protocol applied
                if (vaccine.getProtocolApplied().size() > 0){
                    activity.vaccineKrankheit.add(vaccine.getProtocolApplied().get(0).getTargetDisease().toString());
                }else {
                    activity.vaccineKrankheit.add("");
                }*/
            }
            System.out.println(activity.vaccineKrankheit);

            //get the references
            new VaccineTask1(activity).execute();


            //activity.rvVaccine = activity.findViewById(R.id.rvVaccine);

            //VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.vaccineKrankheit, activity.vaccineHersteller, activity.vaccineDate, activity.vaccineLotNumber);
            //activity.rvVaccine.setAdapter(vacAdapter);
            //activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
        }
    }

    private static class VaccineTask1 extends AsyncTask<Void, Object, List<String>> {
        private WeakReference<VaccineActivity> activityReference;



        VaccineTask1(VaccineActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();
            VaccineActivity activity = activityReference.get();

            //List<Patient> list = new ArrayList<Patient>();
            //list.add(gcm.getExamplePatient());

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

            VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.vaccineKrankheit, activity.vaccineHersteller, activity.vaccineDate, activity.vaccineLotNumber);
            activity.rvVaccine.setAdapter(vacAdapter);
            activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
        }
    }
}