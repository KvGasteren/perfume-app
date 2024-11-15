package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "formula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FormulaIngredient> formulaIngredients = new HashSet<>();

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

    public Set<FormulaIngredient> getFormulaIngredients() {
        return formulaIngredients;
    }

    public void setFormulaIngredients(Set<FormulaIngredient> formulaIngredients) {
        this.formulaIngredients = formulaIngredients;
    }
}
