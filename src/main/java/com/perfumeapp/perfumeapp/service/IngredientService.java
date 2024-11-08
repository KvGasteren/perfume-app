package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient getIngredient(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient updateIngredient(Long id, Ingredient updatedIngredient) {
        Ingredient ingredient = getIngredient(id);
        if (ingredient != null) {
            ingredient.setName(updatedIngredient.getName());
            ingredient.setIngredientAllergens(updatedIngredient.getIngredientAllergens());
            return ingredientRepository.save(ingredient);
        }
        return null;
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
