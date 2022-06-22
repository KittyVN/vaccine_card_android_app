package com.example.MOCO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.ImmunizationRecommendation;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CountryActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private Context ctx = this;
    private CountryActivity activity = this;
    private List<String> countryName = new ArrayList<>();

    private ArrayList<String> countryNecessary = new ArrayList<>();
    private ArrayList<String> countryRecommended = new ArrayList<>();

    private List<String> allVaccineTargetDiseases = new ArrayList<>();
    private List<String> allVaccineTargetDiseasesExpiration = new ArrayList<>();
    private ArrayList<Boolean> countryRecommendedBoolean = new ArrayList<>();
    private ArrayList<Boolean> countryNecessaryBoolean = new ArrayList<>();
    private ArrayList<String> countryRecommendedWithoutDescription = new ArrayList<>();


    private RecyclerView rvNecessaryCountry;
    private RecyclerView rvRecommendedCountry;
    private TextView tvCountryName;
    private String enteredSearchCountry;
    private TextView tvRecommendedHint;
    private TextView tvNecessaryHint;
    private ImageView ivGlobe;
    private ImageView ivBigTrafficLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavCountry);
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
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.bottomNavCountry:
                    return true;
            }
            return false;
        });


        ImageButton btnCountrySearch = (ImageButton) findViewById(R.id.btnSearch);
        TextInputEditText tvSearch = (TextInputEditText) findViewById(R.id.textInputCountry);

        tvSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handled = true;
                enteredSearchCountry = Objects.requireNonNull(tvSearch.getText()).toString();
                tvSearch.setText("");
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                new CountryTask(activity).execute();
            }
            return handled;
        });


        btnCountrySearch.setOnClickListener(v -> {
            enteredSearchCountry = tvSearch.getText().toString();
            tvSearch.setText("");
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            new CountryTask(activity).execute();
        });

    }

    private static class CountryTask1 extends AsyncTask<Void, Object, List<ImmunizationRecommendation>> {

        private WeakReference<CountryActivity> activityReference;
        private CountryActivity activity;

        // only retain a weak reference to the activity
        CountryTask1(CountryActivity context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected List<ImmunizationRecommendation> doInBackground(Void... voids) {
            FhirHelper gcm = new FhirHelper();

            //List<Location> listCountries = gcm.getLocations(activity.enteredSearchCountry);
            List<ImmunizationRecommendation> listRecommendations = null;
            if (activity.enteredSearchCountry != null && !activity.enteredSearchCountry.equals("")) {
                listRecommendations = gcm.getRecommendation(activity.enteredSearchCountry);
            }

            return listRecommendations;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(List<ImmunizationRecommendation> recommendations) {

            activity.tvRecommendedHint = activity.findViewById(R.id.tvRecommended);
            activity.tvNecessaryHint = activity.findViewById(R.id.tvNecessary);
            activity.rvNecessaryCountry = activity.findViewById(R.id.rvNecessaryCountry);
            activity.rvRecommendedCountry = activity.findViewById(R.id.rvRecommendedCountry);
            activity.ivGlobe = activity.findViewById(R.id.ivGlobe);
            activity.ivBigTrafficLight = activity.findViewById(R.id.ivBigTrafficLight);
            activity.rvNecessaryCountry = activity.findViewById(R.id.rvNecessaryCountry);
            activity.rvRecommendedCountry = activity.findViewById(R.id.rvRecommendedCountry);
            activity.tvCountryName = activity.findViewById(R.id.tvCountryName);
            activity.countryName.clear();
            activity.countryNecessary.clear();
            activity.countryRecommended.clear();
            activity.countryNecessaryBoolean.clear();
            activity.countryRecommendedBoolean.clear();
            activity.countryRecommendedWithoutDescription.clear();
            activity.rvRecommendedCountry.setVisibility(View.INVISIBLE);
            activity.rvNecessaryCountry.setVisibility(View.INVISIBLE);
            activity.tvRecommendedHint.setVisibility(View.INVISIBLE);
            activity.tvNecessaryHint.setVisibility(View.INVISIBLE);

            if (recommendations != null && recommendations.size() == 1) {
                for (ImmunizationRecommendation recommendation : recommendations) {

                    if (recommendation.getExtension().size() > 0) {
                        activity.countryName.add(recommendation.getExtension().get(0).getValue().toString());
                    }

                    for (int i = 0; i < recommendation.getRecommendation().size(); i++) {
                        if (recommendation.getRecommendation().get(i).hasDescription()) {
                            activity.countryRecommended.add(recommendation.getRecommendation().get(i).getDescription());
                            activity.countryRecommendedWithoutDescription.add(recommendation.getRecommendation().get(i).getTargetDisease().getCoding().get(0).getDisplay());
                        } else if (!recommendation.getRecommendation().get(i).hasDescription()) {
                            activity.countryNecessary.add(recommendation.getRecommendation().get(i).getTargetDisease().getCoding().get(0).getDisplay());
                        }
                    }
                }
                CountryNecessaryAdapter NAdapter;
                CountryRecommendedAdapter RAdapter;

                //set visibility of of hints above recyclers

                activity.tvRecommendedHint.setVisibility(View.VISIBLE);
                activity.tvNecessaryHint.setVisibility(View.VISIBLE);


                activity.rvRecommendedCountry.setVisibility(View.VISIBLE);
                activity.rvNecessaryCountry.setVisibility(View.VISIBLE);


                activity.ivGlobe.setVisibility(View.INVISIBLE);


                int counter;
                for (int j = 0; j < activity.countryNecessary.size(); j++) {
                    counter = 0;
                    for (int i = 0; i < activity.allVaccineTargetDiseases.size(); i++) {
                        if (activity.allVaccineTargetDiseases.get(i).equals(activity.countryNecessary.get(j))) {

                            String expirationDateTemp = activity.allVaccineTargetDiseasesExpiration.get(i);
                            Date c = new Date();
                            try {
                                Date e = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateTemp);
                                if (c.before(e)) {
                                    counter = counter + 1;
                                    break;
                                }
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                    if (counter > 0) {
                        activity.countryNecessaryBoolean.add(true);
                    } else {
                        activity.countryNecessaryBoolean.add(false);
                    }
                }

                for (int j = 0; j < activity.countryRecommendedWithoutDescription.size(); j++) {
                    counter = 0;
                    for (int i = 0; i < activity.allVaccineTargetDiseases.size(); i++) {
                        if (activity.allVaccineTargetDiseases.get(i).equals(activity.countryRecommendedWithoutDescription.get(j))) {
                            String expirationDateTemp = activity.allVaccineTargetDiseasesExpiration.get(i);
                            Date c = new Date();
                            try {
                                Date e = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateTemp);
                                if (c.before(e)) {
                                    counter = counter + 1;
                                    break;
                                }
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (counter > 0) {
                        activity.countryRecommendedBoolean.add(true);
                    } else {
                        activity.countryRecommendedBoolean.add(false);
                    }
                }


                NAdapter = new CountryNecessaryAdapter(activity.ctx, activity.countryNecessary, activity.countryNecessaryBoolean);
                RAdapter = new CountryRecommendedAdapter(activity.ctx, activity.countryRecommended, activity.countryRecommendedBoolean, activity.countryRecommendedWithoutDescription);

                boolean hasAllNecessary = true;
                boolean hasAllRecommended = true;
                for (int i = 0; i < activity.countryNecessaryBoolean.size(); i++) {
                    if (!activity.countryNecessaryBoolean.get(i)) {
                        hasAllNecessary = false;
                    }
                }
                for (int i = 0; i < activity.countryRecommendedBoolean.size(); i++) {
                    if (!activity.countryRecommendedBoolean.get(i)) {
                        hasAllRecommended = false;
                    }
                }

                if (hasAllNecessary) {
                    activity.ivBigTrafficLight.setBackgroundResource(R.drawable.ampel_gelb);
                    if (hasAllRecommended) {
                        activity.ivBigTrafficLight.setBackgroundResource(R.drawable.ampel_gruen);
                    }
                } else {
                    activity.ivBigTrafficLight.setBackgroundResource(R.drawable.ampel_rot);
                }


                activity.ivBigTrafficLight.setVisibility(View.VISIBLE);

                activity.tvCountryName.setVisibility(View.VISIBLE);
                activity.tvCountryName.setText(activity.countryName.get(0));


                activity.rvNecessaryCountry.setAdapter(NAdapter);
                activity.rvNecessaryCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));

                activity.rvRecommendedCountry.setAdapter(RAdapter);
                activity.rvRecommendedCountry.setLayoutManager(new LinearLayoutManager(activity.ctx));
            } else {
                activity.ivGlobe.setBackgroundResource(R.drawable.globulierr);
                activity.ivBigTrafficLight.setVisibility(View.INVISIBLE);
                activity.tvCountryName.setVisibility(View.INVISIBLE);
                activity.ivGlobe.setVisibility(View.VISIBLE);
            }
        }
    }

    private static class CountryTask extends AsyncTask<Void, Object, List<Immunization>> {
        private WeakReference<CountryActivity> activityReference;


        CountryTask(CountryActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Immunization> doInBackground(Void... voids) {
            FhirHelper gcm = new FhirHelper();


            return gcm.getAllVaccines();
        }

        @Override
        protected void onPostExecute(List<Immunization> vaccines) {
            CountryActivity activity = activityReference.get();

            for (Immunization vaccine : vaccines) {
                for (int i = 0; i < vaccine.getProtocolApplied().get(0).getTargetDisease().size(); i++) {
                    activity.allVaccineTargetDiseases.add(vaccine.getProtocolApplied().get(0).getTargetDisease().get(i).getCoding().get(0).getDisplay());
                    activity.allVaccineTargetDiseasesExpiration.add(vaccine.getExpirationDateElement().asStringValue());
                }
            }

            new CountryActivity.CountryTask1(activity).execute();

        }
    }

}
