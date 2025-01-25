package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredientDTO));
    }

    @PostMapping("/{ingredientId}/allergens")
    public ResponseEntity<IngredientDTO> addAllergen(
            @PathVariable Long ingredientId, @RequestBody AllergenDTO allergenDTO) {
        return ResponseEntity.ok(ingredientService.addAllergen(ingredientId, allergenDTO));
    }

    @PutMapping("/{ingredientId}")
    public ResponseEntity<IngredientDTO> updateIngredientName(
            @PathVariable Long ingredientId, @RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientService.updateIngredientName(ingredientId, ingredientDTO));
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

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> removeIngredient(@PathVariable Long ingredientId) {
        ingredientService.removeIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        IngredientDTO ingredient = ingredientService.getIngredientById(id);
        return ResponseEntity.ok(ingredient);
    }
}

