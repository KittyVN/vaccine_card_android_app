package com.example.MOCO;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView btmNavView;
    private TextView tvTitleHome;

    FhirContext ctx = FhirContext.forR4();
    //online Server to test
    String serverBase = "http://hapi.fhir.org/baseR4";
    Patient patient = new Patient();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btmNavView = findViewById(R.id.bottomNavigationView);
        btmNavView.setSelectedItemId(R.id.bottomNavHome);
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
                        return true;
                    case R.id.bottomNavCountry:
                        startActivity(new Intent(getApplicationContext(),CountryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });





        //ab hier ist nur mehr probieren

        Intent intent = getIntent();
        String url = intent.getStringExtra("This URL: ");
        System.out.println("Intent onCreate showing: " + url);

    }

        private class showPatient extends AsyncTask<String, String, String> {


            @Override
            protected String doInBackground(String... strings) {

                IGenericClient client = ctx.newRestfulGenericClient(serverBase);
                Patient patient = client.read().resource(Patient.class).withId(1L).execute();
                System.out.println(patient.getGender().toString());
                return patient.getGender().toString();
            }
        }



        private class showData extends AsyncTask<String, String, String> {

                @Override
                protected String doInBackground(String... strings) {
                    System.out.println("Intent doInBackground showing: " + strings[0]);

                    String url = strings[0];

                    IGenericClient client = ctx.newRestfulGenericClient(serverBase);
                    Patient currentPatient = client.read().resource(Patient.class).withUrl(url).execute();

                    String[] ids = currentPatient.getId().split("/");
                    int k = 0;
                    for (String item : ids) {
                        if (k == 5) {
                            String id = item;
                            bundle.putString("ID", item);
                        }
                        k++;
                        System.out.println("ID is: " + item);
                    }


                    String familyName = currentPatient.getName().get(0).getFamily();
                    String givenName = currentPatient.getName().get(0).getGivenAsSingleString();

                    bundle.putString("familyName", familyName);
                    bundle.putString("givenName", givenName);

                    patient.addName(currentPatient.getName().get(0));


                    String DOB = "N/A";
                    if (currentPatient.hasBirthDate()) {
                        DOB = currentPatient.getBirthDate().toString();
                        patient.setBirthDate(currentPatient.getBirthDate());
                    } else {
                        patient.setBirthDate(null);
                    }
                    bundle.putString("DOB", DOB);

                    String gender = "Unknown";
                    if (currentPatient.hasGender()) {
                        gender = currentPatient.getGender().toString();
                        patient.setGender(currentPatient.getGender());
                    }
                    bundle.putString("gender", gender);


                    if (currentPatient.hasAddress()) {
                        Address address = currentPatient.getAddress().get(0);
                        System.out.println("Address is " + address.getText());
                        bundle.putString("address", address.getText());
                        patient.addAddress(address);
                    } else {
//                patient.addAddress().addLine(address);
                    }

                    bundle.putString("url", url);

                    return url;
                }

                @Override
                protected void onPostExecute(String url) {

                    TextView patientNameTextView = (TextView) findViewById(R.id.PatientBasicInfo_patientNameTextView);

                    TextView dobTextView = (TextView) findViewById(R.id.patientBasicInfo_birthDateTextView);
                    TextView genderTextView = (TextView) findViewById(R.id.patientBasicInfo_genderTextView);
                    TextView addressTextView = (TextView) findViewById(R.id.patientBasicInfo_addressTextView);


                    //System.out.println("Intent onPostExecute showing: " + url);
                    String fullName = patient.getName().get(0).getNameAsSingleString();
                    bundle.putString("fullName", fullName);
                    System.out.println(fullName);

                    patientNameTextView.setText(fullName);
                    String DOB = "N/A";
                    if (patient.hasBirthDate()) {
                        DOB = patient.getBirthDate().toString();
                    }
                    dobTextView.setText("Birth Date: " + DOB);

                    String gender = "Unknown";
                    if (patient.hasGender()) {
                        gender = patient.getGender().toString();
                    }

                    String address = "Unknown";
                    String city = "";
                    String state = "";
                    String zip = "";
                    if (patient.hasAddress()) {

                        address = bundle.getString("address");

//                address = patient.getAddress().get(0).getLine().toString();
//                city = patient.getAddress().get(0).getCity();
//                state = patient.getAddress().get(0).getState();
//                zip = patient.getAddress().get(0).getPostalCode();
//                bundle.putString("addressCity", city);
//                bundle.putString("addressState", state);
//                bundle.putString("addressZip", zip);
                        //address = patient.getAddress().get(0).getText();
                    }

                    genderTextView.setText("Gender: " + gender);
                    addressTextView.setText("Address : " + address);
//            addressTextView.setText("Address : " + address + ", " + city + ", " + state + ", " + zip);


                }
            }
}
