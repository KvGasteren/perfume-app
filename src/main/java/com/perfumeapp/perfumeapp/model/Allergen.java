package com.perfumeapp.perfumeapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double maxConcentration;

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

    public Double getMaxConcentration() {
        return maxConcentration;
    }

    public void setMaxConcentration(Double maxConcentration) {
        this.maxConcentration = maxConcentration;
    }

    @Override
    public String toString() {
        return "Allergen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxConcentration=" + maxConcentration +
                '}';
    }
}
