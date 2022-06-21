package com.example.MOCO;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class VaccineDto implements Comparable {

    private String vaccineTitle;
    private String vaccineManufacturer;
    private String vaccineDate;
    private String vaccineLotNumber;
    private String vaccinePerformer;
    private String vaccineDoseNumber;
    private String vaccineSeriesNumberTotal;

    public VaccineDto() {
    }

    public String getVaccineTitle() {
        return vaccineTitle;
    }

    public void setVaccineTitle(String vaccineTitle) {
        this.vaccineTitle = vaccineTitle;
    }

    public String getVaccineManufacturer() {
        return vaccineManufacturer;
    }

    public void setVaccineManufacturer(String vaccineManufacturer) {
        this.vaccineManufacturer = vaccineManufacturer;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineLotNumber() {
        return vaccineLotNumber;
    }

    public void setVaccineLotNumber(String vaccineLotNumber) {
        this.vaccineLotNumber = vaccineLotNumber;
    }

    public String getVaccinePerformer() {
        return vaccinePerformer;
    }

    public void setVaccinePerformer(String vaccinePerformer) {
        this.vaccinePerformer = vaccinePerformer;
    }

    public String getVaccineDoseNumber() {
        return vaccineDoseNumber;
    }

    public void setVaccineDoseNumber(String vaccineDoseNumber) {
        this.vaccineDoseNumber = vaccineDoseNumber;
    }

    public String getVaccineSeriesNumberTotal() {
        return vaccineSeriesNumberTotal;
    }

    public void setVaccineSeriesNumberTotal(String vaccineSeriesNumberTotal) {
        this.vaccineSeriesNumberTotal = vaccineSeriesNumberTotal;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineDto that = (VaccineDto) o;
        return Objects.equals(vaccineTitle, that.vaccineTitle) && Objects.equals(vaccineManufacturer, that.vaccineManufacturer) && Objects.equals(vaccineDate, that.vaccineDate) && Objects.equals(vaccineLotNumber, that.vaccineLotNumber) && Objects.equals(vaccinePerformer, that.vaccinePerformer) && Objects.equals(vaccineDoseNumber, that.vaccineDoseNumber) && Objects.equals(vaccineSeriesNumberTotal, that.vaccineSeriesNumberTotal);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(vaccineTitle, vaccineManufacturer, vaccineDate, vaccineLotNumber, vaccinePerformer, vaccineDoseNumber, vaccineSeriesNumberTotal);
    }


    @Override
    public String toString() {
        return "VaccineDto{" +
                "vaccineTitle='" + vaccineTitle + '\'' +
                ", vaccineManufacturer='" + vaccineManufacturer + '\'' +
                ", vaccineDate='" + vaccineDate + '\'' +
                ", vaccineLotNumber='" + vaccineLotNumber + '\'' +
                ", vaccinePerformer='" + vaccinePerformer + '\'' +
                ", vaccineDoseNumber='" + vaccineDoseNumber + '\'' +
                ", vaccineSeriesNumberTotal='" + vaccineSeriesNumberTotal + '\'' +
                '}';
    }

    /**
     * Compares the given VaccineDtos so that the it c´gets sorted by date in a reverse order.
     *
     * @param o will be compared against this
     * @return the compare value
     */
    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(VaccineDto.class)) {
            return vaccineDate.compareTo(((VaccineDto) o).vaccineDate) * -1;
        } else {
            return 1;
        }
    }
}
