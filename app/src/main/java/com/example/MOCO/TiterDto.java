package com.example.MOCO;

import java.util.Objects;

public class TiterDto {

    private String titerTyp;
    private String titerLabor;
    private String titerDate;
    private String titerValue;

    public TiterDto() {
    }

    public String getTiterTyp() {
        return titerTyp;
    }

    public void setTiterTyp(String titerTyp) {
        this.titerTyp = titerTyp;
    }

    public String getTiterLabor() {
        return titerLabor;
    }

    public void setTiterLabor(String titerLabor) {
        this.titerLabor = titerLabor;
    }

    public String getTiterDate() {
        return titerDate;
    }

    public void setTiterDate(String titerDate) {
        this.titerDate = titerDate;
    }

    public String getTiterValue() {
        return titerValue;
    }

    public void setTiterValue(String titerValue) {
        this.titerValue = titerValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiterDto titerDto = (TiterDto) o;
        return Objects.equals(titerTyp, titerDto.titerTyp) && Objects.equals(titerLabor, titerDto.titerLabor) && Objects.equals(titerDate, titerDto.titerDate) && Objects.equals(titerValue, titerDto.titerValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titerTyp, titerLabor, titerDate, titerValue);
    }

    @Override
    public String toString() {
        return "TiterDto{" +
                "titerTyp='" + titerTyp + '\'' +
                ", titerLabor='" + titerLabor + '\'' +
                ", titerDate='" + titerDate + '\'' +
                ", titerValue='" + titerValue + '\'' +
                '}';
    }
}
