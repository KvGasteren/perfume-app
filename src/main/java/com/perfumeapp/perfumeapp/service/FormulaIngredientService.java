package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormulaIngredientService {
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public FormulaIngredient addIngredientToFormula(Long formulaId, FormulaIngredient ingredientData) {
        Formula formula = formulaRepository.findById(formulaId).orElse(null);
        if (formula == null) return null;

        Ingredient ingredient = ingredientRepository.findById(ingredientData.getId()).orElse(null);
        if (ingredient == null) return null;

        ingredientData.setFormula(formula);
        ingredientData.setIngredient(ingredient);
        formula.getFormulaIngredients().add(ingredientData);
        formulaRepository.save(formula);
        return ingredientData;
    }

    public FormulaIngredient updateFormulaIngredient(Long formulaId, Long ingredientId, double newAmount) {
        Formula formula = formulaRepository.findById(formulaId).orElse(null);
        if (formula == null) return null;

        for (FormulaIngredient fi : formula.getFormulaIngredients()) {
            if (fi.getIngredient().getId().equals(ingredientId)) {
                fi.setAmount(newAmount);
                formulaRepository.save(formula);
                return fi;
            }
        }
        return null;
    }

    public boolean removeIngredientFromFormula(Long formulaId, Long ingredientId) {
        Formula formula = formulaRepository.findById(formulaId).orElse(null);
        if (formula == null) return false;

        boolean removed = formula.getFormulaIngredients()
                .removeIf(fi -> fi.getIngredient().getId().equals(ingredientId));
        formulaRepository.save(formula);
        return removed;
    }

    public List<FormulaIngredient> getIngredientsForFormula(Long formulaId) {
        Formula formula = formulaRepository.findById(formulaId).orElse(null);
        return formula != null ? formula.getFormulaIngredients() : null;
    }
}
