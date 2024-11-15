package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping()
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @GetMapping()
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long ingredientId) {
        return ingredientService.getIngredientById(ingredientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{ingredientId}")
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable Long ingredientId, @RequestBody Ingredient ingredientDetails) {
        return ingredientService.getIngredientById(ingredientId)
                .map(ingredient -> ResponseEntity.ok(
                        ingredientService.updateIngredient(ingredientId, ingredientDetails))
                ).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{ingredientId}/add-allergen")
    public ResponseEntity<IngredientAllergen> addAllergenToIngredient(
            @PathVariable Long ingredientId,
            @RequestParam Long allergenId,
            @RequestParam double concentration) {
        IngredientAllergen ingredientAllergen = ingredientService.addAllergenToIngredient(
                ingredientId, allergenId, concentration
        );
        return ResponseEntity.ok(ingredientAllergen);
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }

}
