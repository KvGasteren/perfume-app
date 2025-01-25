package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.FormulaDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.service.FormulaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formulas")
public class FormulaController {

    private FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    @PostMapping()
    public ResponseEntity<FormulaDTO> createFormula(@RequestBody FormulaDTO formulaDTO) {
        return ResponseEntity.ok(formulaService.createFormula(formulaDTO));
    }

    @PostMapping("/{formulaId}/ingredients")
    public ResponseEntity<FormulaDTO> allIngredient(
            @PathVariable Long formulaId, @RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(formulaService.addIngredient(formulaId, ingredientDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<FormulaDTO> updateFormula(
            @PathVariable Long id, @RequestBody FormulaDTO formulaDTO) {
        return ResponseEntity.ok(formulaService.updateFormulaName(id, formulaDTO));
    }

    @PutMapping("/{formulaId}/ingredients/{ingredientId}")
    public ResponseEntity<FormulaDTO> updateIngredientConcentration(
            @PathVariable Long formulaId,
            @PathVariable Long ingredientId,
            @RequestBody double concentration) {
        return ResponseEntity.ok(formulaService.updateIngredientConcentration(
                formulaId, ingredientId, concentration));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormula(@PathVariable Long id) {
        formulaService.removeFormula(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{formulaId}/ingredients/{ingredientId}")
    public ResponseEntity<FormulaDTO> removeIngredient(
            @PathVariable Long formulaId, @PathVariable Long ingredientId) {
        return ResponseEntity.ok(formulaService.removeIngredient(formulaId, ingredientId));
    }

    @GetMapping
    public ResponseEntity<List<FormulaDTO>> getAllFormulas() {
        List<FormulaDTO> formulas = formulaService.getAllFormulas();
        return ResponseEntity.ok(formulas);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FormulaDTO> getFormula(@PathVariable Long id) {
        return ResponseEntity.ok(formulaService.getFormulaById(id));
    }

}
