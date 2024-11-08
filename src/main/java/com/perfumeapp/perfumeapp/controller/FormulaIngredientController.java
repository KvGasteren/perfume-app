package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.service.FormulaIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/formulas/{formulaId}/ingredients")
public class FormulaIngredientController {

    @Autowired
    private FormulaIngredientService formulaIngredientService;

    @PostMapping
    public ResponseEntity<FormulaIngredient> addIngredientToFormula(
            @PathVariable Long formulaId,
            @RequestBody FormulaIngredient ingredientData) {
        FormulaIngredient savedIngredient = formulaIngredientService
                .addIngredientToFormula(formulaId, ingredientData);
        return savedIngredient != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{ingredientId}")
    public ResponseEntity<FormulaIngredient> updateIngredientInFormula(
            @PathVariable Long formulaId,
            @PathVariable Long ingredientId,
            @RequestBody double newAmount) {
        FormulaIngredient updatedIngredient = formulaIngredientService
                .updateFormulaIngredient(formulaId, ingredientId, newAmount);
        return updatedIngredient != null
                ? ResponseEntity.ok(updatedIngredient)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> removeIngredientFromFormula(
            @PathVariable Long formulaId,
            @PathVariable Long ingredientId) {
        boolean removed = formulaIngredientService.removeIngredientFromFormula(formulaId, ingredientId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<FormulaIngredient>> getIngredientsForFormula(@PathVariable Long formulaId) {
        List<FormulaIngredient> ingredients = formulaIngredientService.getIngredientsForFormula(formulaId);
        return ingredients != null ? ResponseEntity.ok(ingredients) : ResponseEntity.notFound().build();
    }

}
