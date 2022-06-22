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
        FhirHelper FhirHelper = new FhirHelper();

        assertEquals(FhirHelper.getAllVaccines().size(),20);
    }

    @Test
    public void getVaccinesSearch(){
        FhirHelper FhirHelper = new FhirHelper();

        assertEquals(FhirHelper.getVaccines("diph").size(),5);
    }

    @Test
    public void getAllTiters(){
        FhirHelper FhirHelper = new FhirHelper();

        assertEquals(FhirHelper.getAllTiters().size(),5);
    }

    @Test
    public void getTiterSearch(){
        FhirHelper FhirHelper = new FhirHelper();

        assertEquals(FhirHelper.getTiter("diph").size(),0);
        assertEquals(FhirHelper.getTiter("s").size(),3);
    }

    @Test
    public void getRecommendation(){
        FhirHelper FhirHelper = new FhirHelper();

        assertEquals(FhirHelper.getRecommendation("Brazil").size(),1);
    }
}
