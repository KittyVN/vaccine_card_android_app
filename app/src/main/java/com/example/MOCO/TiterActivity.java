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

import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Type;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TiterActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private List<String> titerTyp = new ArrayList<String>();
    private List<String> titerLabor = new ArrayList<String>();
    private List<String> titerDate = new ArrayList<String>();
    private List<String> titerValue = new ArrayList<String>();


    private RecyclerView rvTiter;
    private Context ctx = this;

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

        new TiterTask(this).execute();

    }

    private static class TiterTask extends AsyncTask<Void, Object, List<Observation>> {

        private WeakReference<TiterActivity> activityReference;

        // only retain a weak reference to the activity
        TiterTask(TiterActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected List<Observation> doInBackground(Void... voids) {
            VaccineFhirHelper gcm = new VaccineFhirHelper();

            //return list;
            List<Observation> listTiters = gcm.getAllTiters();

            return listTiters;
        }
        @Override
        protected void onPostExecute(List<Observation> titers) {
            TiterActivity activity = activityReference.get();

            for (Observation titer : titers) {
                activity.titerTyp.add(titer.getCode().getText());
                if (titer.getEffective() != null && titer.getEffective().fhirType() == "dateTime"){
                    activity.titerDate.add(titer.getEffectiveDateTimeType().asStringValue());
                }else if (titer.getEffective() != null && titer.getEffective().fhirType() == "Period"){
                    activity.titerDate.add(titer.getEffectivePeriod().getEnd().toString());
                }else activity.titerDate.add("empty");


                if (titer.getValue() != null && titer.getValue().fhirType() == "Quantity"){
                    activity.titerValue.add(titer.getValueQuantity().getValue().toString() + " " + titer.getValueQuantity().getUnit());
                }else if (titer.getValue() != null){
                    activity.titerValue.add(titer.getValue().fhirType());
                }else {
                    activity.titerValue.add("no value");
                }

                if (titer.getPerformer().size() > 0){
                    activity.titerLabor.add(titer.getPerformer().get(0).getDisplay());
                }else {
                    activity.titerLabor.add("Unknown");
                }

            }
            activity.rvTiter = activity.findViewById(R.id.rvTiter);

            TiterAdapter titerAdapter = new TiterAdapter(activity.ctx, activity.titerTyp, activity.titerLabor, activity.titerDate, activity.titerValue);
            activity.rvTiter.setAdapter(titerAdapter);
            activity.rvTiter.setLayoutManager(new LinearLayoutManager(activity.ctx));
        }
    }
}