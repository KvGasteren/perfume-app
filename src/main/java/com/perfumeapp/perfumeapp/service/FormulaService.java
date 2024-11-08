package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public Formula createFormula(Formula formula) {
        return formulaRepository.save(formula);
    }

    public Formula getFormula(Long id) {
        return formulaRepository.findById(id).orElse(null);
    }

    public List<Formula> getAllFormulas() {
        return formulaRepository.findAll();
    }

    public Formula updateFormula(Long id, Formula updatedFormula) {
        Formula formula = getFormula(id);
        if (formula != null) {
            formula.setFormulaIngredients(updatedFormula.getFormulaIngredients());
            return formulaRepository.save(formula);
        }
        return null;
    }

    public void deleteFormula(Long id) {
        formulaRepository.deleteById(id);
    }

    public Map<String, Double> calculateAllergenComposition(Formula formula) {
        Map<String, Double> allergenTotals = new HashMap<>();
        double totalAmount = formula.getFormulaIngredients().stream()
                .mapToDouble(FormulaIngredient::getAmount)
                .sum();

        for (FormulaIngredient fi : formula.getFormulaIngredients()) {
            double ingredientAmount = fi.getAmount();
            Ingredient ingredient = ingredientRepository.findById(fi.getIngredient().getId()).orElse(null);
            if (ingredient != null) {
                for (IngredientAllergen ia : ingredient.getIngredientAllergens()) {
                    String allergenName = ia.getAllergen().getName();
                    double concentration = ia.getConcentration();

                    // Calculate the allergen's contribution to the formula
                    double allergenContribution = (ingredientAmount / totalAmount) * concentration;
                    allergenTotals.put(allergenName, allergenTotals.getOrDefault(allergenName, 0.0) + allergenContribution);
                }
            }
        }
        return allergenTotals;
    }
}


