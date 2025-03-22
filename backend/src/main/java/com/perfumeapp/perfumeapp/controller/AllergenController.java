package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.service.AllergenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<AllergenDTO> createAllergen(@Valid @RequestBody AllergenDTO allergenDTO) {
        AllergenDTO createdAllergen = allergenService.createAllergen(allergenDTO);
        return ResponseEntity
                .created(URI.create("/api/allergens/" + createdAllergen.getId()))
                .body(createdAllergen);
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
