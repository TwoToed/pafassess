package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Form {

    public Form(String country, Integer numperson, Integer minprice, Integer maxprice) {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumperson() {
        return numperson;
    }

    public void setNumperson(int numperson) {
        this.numperson = numperson;
    }

    public int getMinprice() {
        return minprice;
    }

    public void setMinprice(int minprice) {
        this.minprice = minprice;
    }

    public int getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(int maxprice) {
        this.maxprice = maxprice;
    }

    @NotNull(message="must not be null")
    private String country;

    @Min(value=1, message="must at least be 1")
    @Max(value=10, message="cannot be more than 10")
    private int numperson;

    @Min(value=1, message="must be at least 1")
    @Max(value=10000, message="cannot be more than 10000")
    private int minprice;
    
    @Min(value=1, message="must be at least 1")
    @Max(value=10000, message="cannot be more than 10000")
    private int maxprice;
}
