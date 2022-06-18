package com.example.MOCO;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.ImmunizationRecommendation;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import ca.uhn.fhir.util.BundleUtil;

public class VaccineFhirHelper {

        private IGenericClient client;
        public FhirContext ctx;

        private String url2 = "https://hapi.fhir.org/baseR4";
        private String url3 = "http://localhost:8080/fhir";
        private String url4 = "https://spark.incendi.no/fhir";
        private String url5 = "https://sqlonfhir-r4.azurewebsites.net/fhir";
        private String url1 = "https://server.fire.ly/r4";

        private String org = "691117c307504e6e8428e8ad4520bcf6";
        private String patient ="f21ffe610a074c679c417e10950fd633";
/*
   org100 = b6eba60b76774ca4942809e03bc0787e
   org200 = 19a850afb1d24902ba94d940408ed713
   org300 = f752a54b44654a82a0d805b293439a7b
   patient = bce2ef51d0804acc98cf655a5863edf4
    */
        public VaccineFhirHelper() {
            ctx = FhirContext.forR4();
            client = ctx.newRestfulGenericClient(url1);
        }

        public List<Immunization> getVacciness() {
            // Invoke the client
            Bundle bundle = client.search().forResource(Immunization.class)
                    .where(new TokenClientParam("patient").exactly().code("f21ffe610a074c679c417e10950fd633"))
                    .prettyPrint()
                    .returnBundle(Bundle.class)
                    .execute();
            return BundleUtil.toListOfResourcesOfType(ctx, bundle, Immunization.class);
        }

    public List<Immunization> getAllVacciness() {
        // Invoke the client
        Bundle bundle = client.search().forResource(Immunization.class)
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Immunization.class);
    }

    public List<Immunization> getVaccinesTest(){
        Bundle bundle = client.search().forResource(Immunization.class)
                .where(new TokenClientParam("_id").exactly().code("ddbb5bfd-da9b-42a7-a1fb-79e08a16f394"))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Immunization.class);
    }

        public List<Observation> getTiters() {
            // Invoke the client
            Bundle bundle = client.search().forResource(Observation.class)
                    .where(new TokenClientParam("_id").exactly().code("2177826"))
                    .prettyPrint()
                    .returnBundle(Bundle.class)
                    .execute();
            return BundleUtil.toListOfResourcesOfType(ctx, bundle, Observation.class);
        }

    public List<Observation> getAllTiters() {
        // Invoke the client
        Bundle bundle = client.search().forResource(Observation.class)
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Observation.class);
    }

    public List<Observation> getTiterTest() {
        Bundle bundle = client.search().forResource(Observation.class)
                .where(new TokenClientParam("_id").exactly().code("7fdce00a-4b7e-459d-8185-280243417e9c"))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Observation.class);
    }

        public List<Location> getLocations(String country){
            Bundle bundle = client.search().forResource(Location.class)
                    .where(new TokenClientParam("name").exactly().code(country))
                    .prettyPrint()
                    .returnBundle(Bundle.class)
                    .execute();
            return BundleUtil.toListOfResourcesOfType(ctx, bundle, Location.class);
        }

    public List<ImmunizationRecommendation> getAllRecommendations(){
        Bundle bundle = client.search().forResource(ImmunizationRecommendation.class)
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, ImmunizationRecommendation.class);
    }

    public List<ImmunizationRecommendation> getRecommendationsTest(){
        Bundle bundle = client.search().forResource(ImmunizationRecommendation.class)
                .where(new TokenClientParam("_id").exactly().code("a01d428f-2da5-462d-806b-6b7175068901"))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, ImmunizationRecommendation.class);
    }


    public Organization getOrganization(String ref){
            return client.read().resource(Organization.class).withId(ref).execute();
        }

    public Location getLocation(String ref){
        return client.read().resource(Location.class).withId(ref).execute();
    }


        public Patient getExamplePatient(){
            return client.read().resource(Patient.class).withId("example").execute();
        }

        public IGenericClient getClient() {
            return client;
        }

}
