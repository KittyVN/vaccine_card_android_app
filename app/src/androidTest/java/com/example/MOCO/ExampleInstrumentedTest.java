package com.example.MOCO;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.myfirstapp", appContext.getPackageName());
    }

    @Test
    public void getAllVaccines(){
        VaccineFhirHelper vaccineFhirHelper = new VaccineFhirHelper();

        assertEquals(vaccineFhirHelper.getAllVacciness().size(),12);
    }

    @Test
    public void getVaccinesSearch(){
        VaccineFhirHelper vaccineFhirHelper = new VaccineFhirHelper();

        assertEquals(vaccineFhirHelper.getVacciness("diph").size(),5);
    }

    @Test
    public void getAllTiters(){
        VaccineFhirHelper vaccineFhirHelper = new VaccineFhirHelper();

        assertEquals(vaccineFhirHelper.getAllTiters().size(),5);
    }

    @Test
    public void getAllRecommendations(){
        VaccineFhirHelper vaccineFhirHelper = new VaccineFhirHelper();

        assertEquals(vaccineFhirHelper.getAllRecommendations().size(),4);
    }
}
