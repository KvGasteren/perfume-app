package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.*;

@Entity
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double concentrationLimit;

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

    public double getConcentrationLimit() {
        return concentrationLimit;
    }

    public void setConcentrationLimit(double concentrationLimit) {
        this.concentrationLimit = concentrationLimit;
    }
}
