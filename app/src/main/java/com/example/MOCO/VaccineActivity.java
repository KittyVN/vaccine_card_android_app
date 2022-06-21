package com.example.MOCO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import org.hl7.fhir.r4.model.Immunization;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VaccineActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;

    private List<VaccineDto> allVaccines = new ArrayList<>();


    private RecyclerView rvVaccine;
    private Context ctx = this;
    private VaccineActivity activity = this;
    private String enteredSearchTarget;
    TextView tvStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavVaccine);
        btmNavView.setOnNavigationItemSelectedListener(menuItem -> {

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
        });

        TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputVaccine);
        ImageButton btnCountrySearch = (ImageButton) findViewById(R.id.btnSearch1);
        tvStatus = (TextView) findViewById(R.id.tvStatusSearchVaccine);
        tvStatus.setText("Loading...");
        new VaccineTask(activity).execute();


        tvSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handled = true;
                enteredSearchTarget = tvSearch.getText().toString();
                tvStatus.setText("Loading...");
                tvSearch.setText("");
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                new VaccineTask(activity).execute();

            }
            return handled;
        });


        btnCountrySearch.setOnClickListener(v -> {
            enteredSearchTarget = tvSearch.getText().toString();
            tvSearch.setText("");
            tvStatus.setText("Loading...");
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            new VaccineTask(activity).execute();
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
            FhirHelper gcm = new FhirHelper();
            List<Immunization> listVaccines;


            if (activity.enteredSearchTarget == "" || activity.enteredSearchTarget == null) {
                listVaccines = gcm.getAllVaccines();
            } else {
                listVaccines = gcm.getVaccines(activity.enteredSearchTarget);
            }

            return listVaccines;
        }

        @Override
        protected void onPostExecute(List<Immunization> vaccines) {
            VaccineActivity activity = activityReference.get();

            activity.allVaccines.clear();

            if (vaccines.size() != 0) {
                for (Immunization vaccine : vaccines) {
                    VaccineDto vaccineDto = new VaccineDto();
                    vaccineDto.setVaccineTitle(vaccine.getVaccineCode().getCoding().get(0).getDisplay());
                    vaccineDto.setVaccineManufacturer(vaccine.getManufacturer().getDisplay());
                    vaccineDto.setVaccineDate(vaccine.getOccurrenceDateTimeType().asStringValue());
                    vaccineDto.setVaccineLotNumber(vaccine.getLotNumber());
                    if (vaccine.getPerformer().size() > 0) {
                        vaccineDto.setVaccinePerformer(vaccine.getPerformer().get(0).getActor().getDisplay());
                    } else {
                        vaccineDto.setVaccinePerformer("Unknown");
                    }
                    if (vaccine.getProtocolApplied().size() > 0) {
                        vaccineDto.setVaccineDoseNumber(vaccine.getProtocolApplied().get(0).
                                getDoseNumberPositiveIntType().asStringValue());
                        vaccineDto.setVaccineSeriesNumberTotal(vaccine.getProtocolApplied().get(0).
                                getSeriesDosesPositiveIntType().asStringValue());
                    } else {
                        vaccineDto.setVaccineDoseNumber("Unknown");
                        vaccineDto.setVaccineSeriesNumberTotal("Unknown");
                    }
                    activity.allVaccines.add(vaccineDto);
                }
                Collections.sort(activity.allVaccines);


                activity.rvVaccine = activity.findViewById(R.id.rvVaccine);
                activity.tvStatus.setText("");

                VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.allVaccines);
                activity.rvVaccine.setAdapter(vacAdapter);
                activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
            }else {
                activity.rvVaccine = activity.findViewById(R.id.rvVaccine);

                VaccineAdapter vacAdapter = new VaccineAdapter(activity.ctx, activity.allVaccines);
                activity.rvVaccine.setAdapter(vacAdapter);
                activity.rvVaccine.setLayoutManager(new LinearLayoutManager(activity.ctx));
                activity.tvStatus.setText("Nothing found");
            }

        }
    }
}
