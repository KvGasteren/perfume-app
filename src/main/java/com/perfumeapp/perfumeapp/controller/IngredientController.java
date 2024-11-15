package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
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

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredientDTO));
    }

    @PostMapping("/{ingredientId}/allergens")
    public ResponseEntity<IngredientDTO> addAllergen(
            @PathVariable Long ingredientId, @RequestBody AllergenDTO allergenDTO) {
        return ResponseEntity.ok(ingredientService.addAllergen(ingredientId, allergenDTO));
    }

    @PutMapping("/{ingredientId}/allergens/{allergenId}")
    public ResponseEntity<IngredientDTO> updateAllergenConcentration(
            @PathVariable Long ingredientId, @PathVariable Long allergenId, @RequestBody double concentration) {
        return ResponseEntity.ok(ingredientService.updateAllergenConcentration(ingredientId, allergenId, concentration));
    }

    @DeleteMapping("/{ingredientId}/allergens/{allergenId}")
    public ResponseEntity<IngredientDTO> removeAllergen(
            @PathVariable Long ingredientId, @PathVariable Long allergenId) {
        return ResponseEntity.ok(ingredientService.removeAllergen(ingredientId, allergenId));
    }
}

