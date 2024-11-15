package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    public Formula createFormula(Formula formula) {
        return formulaRepository.save(formula);
    }

    public List<Formula> getAllFormulas() {
        return formulaRepository.findAll();
    }

    public Optional<Formula> getFormulaById(Long id) {
        return formulaRepository.findById(id);
    }

    public Formula updateFormula(Long id, Formula formulaDetails) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        if (formulaDetails.getName() != null) {
            formula.setName(formulaDetails.getName());
        }
        return formulaRepository.save(formula);
    }

    public void deleteFormula(Long id) {
        formulaRepository.deleteById(id);
    }

    // Method to create or update formula and add ingredients
    public Formula addIngredientsToFormula(Long formulaId, Set<Long> ingredientIds) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));

        // Fetch ingredients based on the provided ingredientIds
        Set<Ingredient> ingredients = new HashSet<>();
        for (Long ingredientId : ingredientIds) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new RuntimeException("Ingredient not found"));
            ingredients.add(ingredient);
        }

        // Add allergens to the ingredient
        formula.setIngredients(ingredients);

        // Save the updated ingredient
        return formulaRepository.save(formula);
    }
}
