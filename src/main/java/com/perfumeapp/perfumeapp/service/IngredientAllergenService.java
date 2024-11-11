package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientAllergenService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private AllergenRepository allergenRepository;

//    public Ingredient addAllergenToIngredient(Long ingredientId, IngredientAllergen allergenData) {
//        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
//        if (ingredient == null) return null;
//
//        Allergen allergen = allergenRepository.findById(allergenData.getAllergen().getId()).orElse(null);
//        if (allergen == null) return null;
//
//        allergenData.setIngredient(ingredient);
//        allergenData.setAllergen(allergen);
//        ingredient.getIngredientAllergens().add(allergenData);
//        ingredientRepository.save(ingredient);
//        return ingredient;
//    }
public Ingredient addAllergenToIngredient(Long ingredientId, IngredientAllergen allergenData) {
    // Fetch the ingredient by ID
    Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
    if (ingredient == null) {
        throw new RuntimeException("Ingredient not found with id: " + ingredientId);
    }

    // Fetch the allergen by allergenId from allergenData
    Long allergenId = allergenData.getAllergen().getId();
    Allergen allergen = allergenRepository.findById(allergenId).orElse(null);
    if (allergen == null) {
        throw new RuntimeException("Allergen not found with id: " + allergenId);
    }

    // Set the allergen and ingredient in allergenData
    allergenData.setAllergen(allergen);
    allergenData.setIngredient(ingredient);

    // Add allergenData to ingredient's list
    ingredient.getIngredientAllergens().add(allergenData);

    // Save the updated ingredient
    return ingredientRepository.save(ingredient);
}

    public Ingredient updateAllergenInIngredient(Long ingredientId, Long allergenId, double newConcentration) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        if (ingredient == null) return null;

        for (IngredientAllergen ia : ingredient.getIngredientAllergens()) {
            if (ia.getAllergen().getId().equals(allergenId)) {
                ia.setConcentration(newConcentration);
                ingredientRepository.save(ingredient);
                return ingredient;
            }
        }
        return null;
    }

    public boolean removeAllergenFromIngredient(Long ingredientId, Long allergenId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        if (ingredient == null) return false;

        boolean removed = ingredient.getIngredientAllergens().removeIf(ia -> ia.getAllergen().getId().equals(allergenId));
        ingredientRepository.save(ingredient);
        return removed;
    }

    public List<IngredientAllergen> getAllergensForIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        return ingredient != null ? ingredient.getIngredientAllergens() : null;
    }
}

