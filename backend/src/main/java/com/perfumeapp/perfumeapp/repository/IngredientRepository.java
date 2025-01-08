package com.perfumeapp.perfumeapp.repository;


import com.perfumeapp.perfumeapp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
