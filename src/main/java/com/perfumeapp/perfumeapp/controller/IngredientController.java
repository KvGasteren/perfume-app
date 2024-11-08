package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        return ingredient != null ? ResponseEntity.ok(ingredient) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        Ingredient ingredient = ingredientService.updateIngredient(id, updatedIngredient);
        return ingredient != null ? ResponseEntity.ok(ingredient) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

}
