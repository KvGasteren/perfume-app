package com.perfumeapp.perfumeapp.dto;


import java.util.List;

public class FormulaDTO {
    private Long id;
    private String name;
    private List<FormulaIngredientDTO> ingredients;
    private List<FormulaAllergenDTO> allergenSummary;

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

    public List<FormulaIngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<FormulaIngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<FormulaAllergenDTO> getAllergenSummary() {
        return allergenSummary;
    }

    public void setAllergenSummary(List<FormulaAllergenDTO> allergenSummary) {
        this.allergenSummary = allergenSummary;
    }
}
