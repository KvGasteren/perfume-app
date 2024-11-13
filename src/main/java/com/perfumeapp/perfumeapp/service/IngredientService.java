package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private AllergenRepository allergenRepository;

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

    // Method to create or update ingredient and add allergens
    public Ingredient addAllergensToIngredient(Long ingredientId, Set<Long> allergenIds) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        // Fetch allergens based on the provided allergenIds
        Set<Allergen> allergens = new HashSet<>();
        for (Long allergenId : allergenIds) {
            Allergen allergen = allergenRepository.findById(allergenId)
                    .orElseThrow(() -> new RuntimeException("Allergen not found"));
            allergens.add(allergen);
        }

        // Add allergens to the ingredient
        ingredient.setAllergens(allergens);

        // Save the updated ingredient
        return ingredientRepository.save(ingredient);
    }
}
