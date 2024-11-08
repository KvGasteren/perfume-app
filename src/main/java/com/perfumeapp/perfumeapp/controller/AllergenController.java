package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.service.AllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergens")
@CrossOrigin("http://localhost:3000")
public class AllergenController {

    @Autowired
    private AllergenService allergenService;

    @PostMapping
    public ResponseEntity<Allergen> createAllergen(@RequestBody Allergen allergen) {
        Allergen createdAllergen = allergenService.createAllergen(allergen);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAllergen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Allergen> getAllergen(@PathVariable Long id) {
        Allergen allergen = allergenService.getAllergen(id);
        return allergen != null ? ResponseEntity.ok(allergen) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Allergen>> getAllAllergens() {
        List<Allergen> allergens = allergenService.getAllAllergens();
        return ResponseEntity.ok(allergens);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Allergen> updateAllergen(@PathVariable Long id, @RequestBody Allergen updatedAllergen) {
        Allergen allergen = allergenService.updateAllergen(id, updatedAllergen);
        return allergen != null ? ResponseEntity.ok(allergen) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long id) {
        allergenService.deleteAllergen(id);
        return ResponseEntity.noContent().build();
    }
}
