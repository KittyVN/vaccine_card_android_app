package com.example.MOCO;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.ImmunizationRecommendation;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.SearchParameter;

import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import ca.uhn.fhir.util.BundleUtil;

public class FhirHelper {

    private IGenericClient client;
    public FhirContext ctx;

    private String url2 = "https://hapi.fhir.org/baseR4";
    private String url3 = "http://localhost:8080/fhir";
    private String url4 = "https://spark.incendi.no/fhir";
    private String url5 = "https://sqlonfhir-r4.azurewebsites.net/fhir";
    private String url1 = "https://server.fire.ly/r4";
    private String ourServerUrl = "http://lukasletuhalmg.synology.me:12345/fhir";


    public FhirHelper() {
        ctx = FhirContext.forR4();
        client = ctx.newRestfulGenericClient(ourServerUrl);
    }

    public void uploadSearchParameter() {
        // Create a search parameter definition
        SearchParameter countrySP = new SearchParameter();
        countrySP.addBase("ImmunizationRecommendation");
        countrySP.setCode("country");
        countrySP.setType(Enumerations.SearchParamType.TOKEN);
        countrySP.setTitle("Country");
        countrySP.setExpression("ImmunizationRecommendation.extension('http://tuwien.ac.at/moco/fhir/StructureDefinition/PlusCountryName')");
        countrySP.setXpathUsage(SearchParameter.XPathUsageType.NORMAL);
        countrySP.setStatus(Enumerations.PublicationStatus.ACTIVE);
        client.create().resource(countrySP).execute();
    }


    public List<Immunization> getVaccines(String vaccine) {
        // Invoke the client

        String searchUrl = "Immunization?vaccine-code:text=" + vaccine;

        Bundle bundle = client.search()
                .byUrl(searchUrl)
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Immunization.class);
    }

    public List<Immunization> getAllVaccines() {
        // Invoke the client
        Bundle bundle = client.search().forResource(Immunization.class)
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Immunization.class);
    }

    public List<Observation> getAllTiters() {
        // Invoke the client
        String searchUrl = "Observation?_sort=-date";

        Bundle bundle = client.search()
                .byUrl(searchUrl)
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Observation.class);
    }

    public List<Observation> getTiter(String titer) {
        // Invoke the client

        String searchUrl = "Observation?_sort=-date&code:text=" + titer;

        Bundle bundle = client.search()
                .byUrl(searchUrl)
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, Observation.class);
    }

    public List<ImmunizationRecommendation> getRecommendation(String country) {
        Bundle bundle = client.search().forResource(ImmunizationRecommendation.class)
                .where(new TokenClientParam("country").exactly().code(country))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();
        return BundleUtil.toListOfResourcesOfType(ctx, bundle, ImmunizationRecommendation.class);
    }

}
