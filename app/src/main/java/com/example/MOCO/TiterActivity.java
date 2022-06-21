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

import org.hl7.fhir.r4.model.Observation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TiterActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;

    private List<TiterDto> allTiter = new ArrayList<>();

    private String enteredSearchTarget;
    private RecyclerView rvTiter;
    private Context ctx = this;
    private TiterActivity activity = this;
    TextView tvStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titer);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavTiter);
        btmNavView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.bottomNavVaccine:
                    startActivity(new Intent(getApplicationContext(), VaccineActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.bottomNavTiter:
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

        TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputTiter);
        ImageButton btnCountrySearch = (ImageButton) findViewById(R.id.btnSearch2);
        tvStatus = (TextView) findViewById(R.id.tvStatusSearchTiter);
        tvStatus.setText("Loading...");
        new TiterTask(this).execute();

        tvSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handled = true;
                enteredSearchTarget = tvSearch.getText().toString();
                tvSearch.setText("");
                tvStatus.setText("Loading...");
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                new TiterTask(activity).execute();

            }
            return handled;
        });


        btnCountrySearch.setOnClickListener(v -> {
            enteredSearchTarget = tvSearch.getText().toString();
            tvSearch.setText("");
            tvStatus.setText("Loading...");
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            new TiterTask(activity).execute();
        });


    }

    private static class TiterTask extends AsyncTask<Void, Object, List<Observation>> {

        private WeakReference<TiterActivity> activityReference;
        private TiterActivity activity;

        // only retain a weak reference to the activity
        TiterTask(TiterActivity context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected List<Observation> doInBackground(Void... voids) {
            FhirHelper gcm = new FhirHelper();

            List<Observation> listTiters;

            if (activity.enteredSearchTarget == "" || activity.enteredSearchTarget == null) {
                listTiters = gcm.getAllTiters();
            } else {
                listTiters = gcm.getTiter(activity.enteredSearchTarget);
            }

            return listTiters;
        }

        @Override
        protected void onPostExecute(List<Observation> titers) {
            TiterActivity activity = activityReference.get();

            activity.allTiter.clear();

            if (titers.size() != 0) {
                for (Observation titer : titers) {
                    TiterDto titerDto = new TiterDto();
                    titerDto.setTiterTyp(titer.getCode().getCoding().get(0).getDisplay());
                    if (titer.getEffective() != null && titer.getEffective().fhirType() == "dateTime") {
                        titerDto.setTiterDate(titer.getEffectiveDateTimeType().asStringValue());
                    } else if (titer.getEffective() != null && titer.getEffective().fhirType() == "Period") {
                        titerDto.setTiterDate(titer.getEffectivePeriod().getEnd().toString());
                    } else titerDto.setTiterDate("empty");

                    if (titer.getValue() != null && titer.getValue().fhirType() == "Quantity") {
                        titerDto.setTiterValue(titer.getValueQuantity().getValue().toString() + " " +
                                titer.getValueQuantity().getCode());
                    } else if (titer.getValue() != null) {
                        titerDto.setTiterValue(titer.getValue().fhirType());
                    } else {
                        titerDto.setTiterValue("no value");
                    }

                    if (titer.getPerformer().size() > 0) {
                        titerDto.setTiterLabor(titer.getPerformer().get(0).getDisplay());
                    } else {
                        titerDto.setTiterLabor("Unknown");
                    }

                    activity.allTiter.add(titerDto);
                }
                activity.rvTiter = activity.findViewById(R.id.rvTiter);

                TiterAdapter titerAdapter = new TiterAdapter(activity.ctx, activity.allTiter);
                activity.rvTiter.setAdapter(titerAdapter);
                activity.rvTiter.setLayoutManager(new LinearLayoutManager(activity.ctx));
            }else {
                activity.rvTiter = activity.findViewById(R.id.rvTiter);

                TiterAdapter titerAdapter = new TiterAdapter(activity.ctx, activity.allTiter);
                activity.rvTiter.setAdapter(titerAdapter);
                activity.rvTiter.setLayoutManager(new LinearLayoutManager(activity.ctx));
                activity.tvStatus.setText("Nothing found");
            }
        }
    }
}