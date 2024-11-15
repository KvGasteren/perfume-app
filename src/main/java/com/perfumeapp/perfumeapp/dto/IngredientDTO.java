package com.perfumeapp.perfumeapp.dto;

import java.util.ArrayList;
import java.util.List;

public class IngredientDTO {
    private Long id;
    private String name;
    private List<AllergenDTO> allergens = new ArrayList<>();
    private double concentration; // specific to this Formula

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

    public List<AllergenDTO> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<AllergenDTO> allergens) {
        this.allergens = allergens;
    }
}
