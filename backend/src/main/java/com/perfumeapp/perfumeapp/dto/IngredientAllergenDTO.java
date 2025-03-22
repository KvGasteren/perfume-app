package com.perfumeapp.perfumeapp.dto;

public class IngredientAllergenDTO {

    private Long id;
    private String name;
    private double maxConcentration;
    private double concentration;

    // Constructor
    public IngredientAllergenDTO() {
    }

    public IngredientAllergenDTO(Long allergenId, String allergenName, double concentration) {
        this.id = allergenId;
        this.name = allergenName;
        this.concentration = concentration;
    }

    // Getters and Setters

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

    public double getMaxConcentration() {
        return maxConcentration;
    }

    public void setAllergenMaxConcentration(Double maxConcentration) {
        this.maxConcentration = maxConcentration;
    }
}
