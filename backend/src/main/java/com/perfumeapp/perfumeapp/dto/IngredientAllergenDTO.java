package com.perfumeapp.perfumeapp.dto;

public class IngredientAllergenDTO {

    private Long allergenId;
    private String allergenName;
    private double maxConcentration;
    private double concentration;

    // Constructor
    public IngredientAllergenDTO() {
    }

    public IngredientAllergenDTO(Long allergenId, String allergenName, double concentration) {
        this.allergenId = allergenId;
        this.allergenName = allergenName;
        this.concentration = concentration;
    }

    // Getters and Setters

    public Long getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }

    public String getAllergenName() {
        return allergenName;
    }

    public void setAllergenName(String allergenName) {
        this.allergenName = allergenName;
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
