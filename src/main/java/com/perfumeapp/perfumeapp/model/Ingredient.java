package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IngredientAllergen> ingredientAllergens = new ArrayList<>();

    // Getters and setters

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

    public List<IngredientAllergen> getIngredientAllergens() {
        return ingredientAllergens;
    }

    public void setIngredientAllergens(List<IngredientAllergen> ingredientAllergens) {
        this.ingredientAllergens = ingredientAllergens;
    }
}