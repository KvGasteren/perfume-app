package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IngredientAllergen> ingredientAllergens;

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

    public Set<IngredientAllergen> getIngredientAllergens() {
        return ingredientAllergens;
    }

    public void setIngredientAllergens(Set<IngredientAllergen> ingredientAllergens) {
        this.ingredientAllergens = ingredientAllergens;
    }
}
