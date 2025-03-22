package com.perfumeapp.perfumeapp.dto;

import java.util.ArrayList;
import java.util.List;

public class FormulaIngredientDTO {

    private Long id;
    private String name;
    private List<IngredientAllergenDTO> allergens = new ArrayList<>();
    private double parts;
    private double concentration;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientAllergenDTO> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<IngredientAllergenDTO> allergens) {
        this.allergens = allergens;
    }

    public double getParts() {
        return parts;
    }

    public void setParts(double parts) {
        this.parts = parts;
    }

    public double getConcentration() { return concentration; }

    public void setConcentration(double concentration) { this.concentration = concentration; }
}
