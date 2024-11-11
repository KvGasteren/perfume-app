
package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.service.IngredientAllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/ingredients/{ingredientId}/allergens")
public class IngredientAllergenController {

    @Autowired
    private IngredientAllergenService ingredientAllergenService;

    // Add an allergen to an ingredient
    @PostMapping
    public ResponseEntity<Ingredient> addAllergenToIngredient(
            @PathVariable Long ingredientId,
            @RequestBody IngredientAllergen allergenData) {
        Ingredient savedIngredient = ingredientAllergenService.addAllergenToIngredient(ingredientId, allergenData);
        return savedIngredient != null ? ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient)
                : ResponseEntity.notFound().build();
    }

    // Update an allergen's concentration within an ingredient
    @PutMapping("/{allergenId}")
    public ResponseEntity<Ingredient> updateAllergenInIngredient(
            @PathVariable Long ingredientId,
            @PathVariable Long allergenId,
            @RequestBody double newConcentration) {
        Ingredient updatedAllergen = ingredientAllergenService.updateAllergenInIngredient(ingredientId, allergenId, newConcentration);
        return updatedAllergen != null ? ResponseEntity.ok(updatedAllergen) : ResponseEntity.notFound().build();
    }

    // Remove an allergen from an ingredient
    @DeleteMapping("/{allergenId}")
    public ResponseEntity<Void> removeAllergenFromIngredient(@PathVariable Long ingredientId, @PathVariable Long allergenId) {
        boolean removed = ingredientAllergenService.removeAllergenFromIngredient(ingredientId, allergenId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get all allergens for a specific ingredient
    @GetMapping
    public ResponseEntity<List<IngredientAllergen>> getAllergensForIngredient(@PathVariable Long ingredientId) {
        List<IngredientAllergen> allergens = ingredientAllergenService.getAllergensForIngredient(ingredientId);
        return allergens != null ? ResponseEntity.ok(allergens) : ResponseEntity.notFound().build();
    }
}
