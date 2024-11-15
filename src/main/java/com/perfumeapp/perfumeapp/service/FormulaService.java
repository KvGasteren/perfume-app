package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.FormulaIngredientRepository;
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
    @Autowired
    private FormulaIngredientRepository formulaIngredientRepository;

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

    public FormulaIngredient addIngredientToFormula(Long formulaId, Long ingredientId, double concentration) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaIngredient.setFormula(formula);
        formulaIngredient.setIngredient(ingredient);
        formulaIngredient.setConcentration(concentration);

        return formulaIngredientRepository.save(formulaIngredient);
    }

}
