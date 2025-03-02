package com.perfumeapp.perfumeapp.dto;

public class AllergenDTO {

    private Long id;
    private String name;
    private double maxConcentration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxConcentration() {
        return maxConcentration;
    }

    public void setMaxConcentration(double maxConcentration) {
        this.maxConcentration = maxConcentration;
    }

}
