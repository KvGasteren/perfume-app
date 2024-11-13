package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient updateIngredient(Long id, Ingredient ingredientDetails) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        if (ingredientDetails.getName() != null) {
            ingredient.setName(ingredientDetails.getName());
        }
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
