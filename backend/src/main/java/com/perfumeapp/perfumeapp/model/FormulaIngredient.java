package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.*;

@Entity(name = "formula_ingredient")
public class FormulaIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formula_id", nullable = false)
    private Formula formula;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "parts", nullable = false)
    private double parts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public double getParts() {
        return parts;
    }

    public void setParts(double concentration) {
        this.parts = concentration;
    }
}
