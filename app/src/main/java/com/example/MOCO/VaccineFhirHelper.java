package com.example.MOCO;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Immunization;
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

        private String url1 = "https://hapi.fhir.org/baseR4";
        private String url2 = "http://localhost:8080/fhir";

        public VaccineFhirHelper() {
            ctx = FhirContext.forR4();
            client = ctx.newRestfulGenericClient(url1);
        }

        public List<Immunization> getVacciness() {
            // Invoke the client
            Bundle bundle = client.search().forResource(Immunization.class)
                    .where(new TokenClientParam("patient").exactly().code("example"))
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

        public Organization getOrganization(String ref){
        return client.read().resource(Organization.class).withId(ref).execute();
        }


        public Patient getExamplePatient(){
            return client.read().resource(Patient.class).withId("example").execute();
        }

        public IGenericClient getClient() {
            return client;
        }

}
