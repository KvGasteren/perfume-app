package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/formulas")
public class FormulaController {

    @Autowired
    private FormulaService formulaService;

    @PostMapping
    public ResponseEntity<Formula> createFormula(@RequestBody Formula formula) {
        Formula createdFormula = formulaService.createFormula(formula);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormula);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formula> getFormula(@PathVariable Long id) {
        Formula formula = formulaService.getFormula(id);
        return formula != null ? ResponseEntity.ok(formula) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Formula>> getAllFormulas() {
        List<Formula> formulas = formulaService.getAllFormulas();
        return ResponseEntity.ok(formulas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formula> updateFormula(@PathVariable Long id, @RequestBody Formula updatedFormula) {
        Formula formula = formulaService.updateFormula(id, updatedFormula);
        return formula != null ? ResponseEntity.ok(formula) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormula(@PathVariable Long id) {
        formulaService.deleteFormula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/allergen-composition")
    public ResponseEntity<Map<String, Double>> getAllergenComposition(@PathVariable Long id) {
        Formula formula = formulaService.getFormula(id);
        if (formula == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Double> composition = formulaService.calculateAllergenComposition(formula);
        return ResponseEntity.ok(composition);
    }
}
