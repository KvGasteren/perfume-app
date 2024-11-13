package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import com.perfumeapp.perfumeapp.service.AllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergens")
public class AllergenController {
    @Autowired
    private AllergenService allergenService;

    @GetMapping()
    public List<Allergen> getAllAllergens() {
        return  allergenService.getAllAllergens();
    }

    @GetMapping("/{allergenId}")
    public ResponseEntity<Allergen> getAllergen(@RequestParam Long allergenId) {
        return allergenService.getAllergenById(allergenId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Allergen createAllergen(@RequestBody Allergen allergen) {
        return allergenService.createAllergen(allergen);
    }

    @PutMapping("/{allergenId}")
    public ResponseEntity<Allergen> updateAllergen(
            @PathVariable Long allergenId,
            @RequestBody Allergen allergenDetails) {
        return allergenService.getAllergenById(allergenId)
                .map(allergen -> ResponseEntity.ok(
                        allergenService.updateAllergen(allergenId, allergenDetails))
                ).orElse(ResponseEntity.notFound().build());
    }

}
