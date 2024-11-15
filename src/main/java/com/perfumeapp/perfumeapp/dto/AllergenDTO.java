package com.perfumeapp.perfumeapp.dto;

public class AllergenDTO {

    private Long id;
    private String name;
    private double concentration; // specific to this Ingredient

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

    public double getConcentration() {
        return concentration;
    }

    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }
}
