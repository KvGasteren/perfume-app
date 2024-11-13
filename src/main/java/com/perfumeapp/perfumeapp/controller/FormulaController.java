package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formulas")
public class FormulaController {
    @Autowired
    private FormulaService formulaService;

    @PostMapping()
    public Formula createFormula(@RequestBody Formula formula) {
        return formulaService.createFormula(formula);
    }

    @GetMapping()
    public List<Formula> getAllFormulas() {
        return formulaService.getAllFormulas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formula> getFormula(@PathVariable Long id) {
        return formulaService.getFormulaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formula> updateFormula(@PathVariable Long id, @RequestBody Formula formulaDetails) {
        Optional<Formula> existingFormulaOptional = formulaService.getFormulaById(id);
        if (existingFormulaOptional.isPresent()) {
            Formula existingFormula = existingFormulaOptional.get();

            if (formulaDetails.getName() != null) {
                existingFormula.setName(formulaDetails.getName());
            }
            Formula updatedFormula = formulaService.updateFormula(id, existingFormula);
            return ResponseEntity.ok(updatedFormula);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormula(@PathVariable Long id) {
        formulaService.deleteFormula(id);
        return ResponseEntity.noContent().build();
    }
}
