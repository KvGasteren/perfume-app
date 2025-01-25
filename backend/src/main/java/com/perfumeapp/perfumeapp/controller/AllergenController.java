package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.service.AllergenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergens")
public class AllergenController {

    private final AllergenService allergenService;

    public AllergenController(AllergenService allergenService) {
        this.allergenService = allergenService;
    }

    @GetMapping()
    public List<AllergenDTO> getAllAllergens() {
        return allergenService.getAllAllergens();
    }

    @GetMapping("/{allergenId}")
    public ResponseEntity<AllergenDTO> getAllergen(@PathVariable Long allergenId) {
        return ResponseEntity.ok(allergenService.getAllergenById(allergenId));
    }

    @PostMapping()
    public ResponseEntity<AllergenDTO> createAllergen(@RequestBody AllergenDTO allergen) {
        return ResponseEntity.ok(allergenService.createAllergen(allergen));
    }

    @PutMapping("/{allergenId}")
    public ResponseEntity<AllergenDTO> updateAllergen(
            @PathVariable Long allergenId,
            @RequestBody AllergenDTO allergenDetails) {
        return ResponseEntity.ok(allergenService.updateAllergen(allergenId, allergenDetails));
    }

    @DeleteMapping("/{allergenId}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long allergenId) {
        allergenService.deleteAllergen(allergenId);
        return ResponseEntity.noContent().build();
    }

}
