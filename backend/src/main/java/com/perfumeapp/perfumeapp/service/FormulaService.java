package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.FormulaDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.exception.ResourceNotFoundException;
import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.model.FormulaIngredient;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormulaService {

    private FormulaRepository formulaRepository;

    public FormulaService(FormulaRepository formulaRepository) {
        this.formulaRepository = formulaRepository;
    }

    @Autowired
    private IngredientRepository ingredientRepository;

    public FormulaDTO createFormula(FormulaDTO formulaDTO) {
        Formula formula = new Formula();
        formula.setName(formulaDTO.getName());
        Formula savedFormula = formulaRepository.save(formula);
        return convertToDTO(savedFormula);
    }

    public FormulaDTO addIngredient(Long formulaId, IngredientDTO ingredientDTO) {
        Formula formula = retrieveFormula(formulaId);
        Ingredient ingredient = ingredientRepository.findById(ingredientDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaIngredient.setFormula(formula);
        formulaIngredient.setIngredient(ingredient);
        formulaIngredient.setConcentration(ingredientDTO.getConcentration());

        formula.getFormulaIngredients().add(formulaIngredient);
        formulaRepository.save(formula);

        return convertToDTO(formula);
    }

    public FormulaDTO updateFormulaName(Long formulaId, FormulaDTO formulaDTO) {
        Formula formula = retrieveFormula(formulaId);
        formula.setName(formulaDTO.getName());
        formulaRepository.save(formula);
        return convertToDTO(formula);
    }

    public FormulaDTO updateIngredientConcentration(Long formulaId, Long ingredientId, double concentration) {
        Formula formula = retrieveFormula(formulaId);

        FormulaIngredient formulaIngredient = formula.getFormulaIngredients().stream()
                .filter(fi -> fi.getIngredient().getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not associated with the formula"));

        formulaIngredient.setConcentration(concentration);
        formulaRepository.save(formula);

        return convertToDTO(formula);
    }

    public FormulaDTO removeIngredient(Long formulaId, Long ingredientId) {
        Formula formula = retrieveFormula(formulaId);
        formula.getFormulaIngredients().removeIf(fi -> fi.getIngredient().getId().equals(ingredientId));
        formulaRepository.save(formula);
        return convertToDTO(formula);
    }

    public void removeFormula(Long id) {
        Formula formula = retrieveFormula(id);
        formulaRepository.delete(formula);
    }

    public FormulaDTO getFormulaById(Long id) {
        Formula formula = retrieveFormula(id);
        return convertToDTO(formula);
    }

    public List<FormulaDTO> getAllFormulas() {
        return formulaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FormulaDTO convertToDTO(Formula formula) {
        FormulaDTO dto = new FormulaDTO();
        dto.setId(formula.getId());
        dto.setName(formula.getName());

        List<IngredientDTO> ingredients = formula.getFormulaIngredients().stream()
                .map(fi -> {
                    Ingredient ingredient = fi.getIngredient();
                    IngredientDTO ingredientDTO = new IngredientDTO();
                    ingredientDTO.setId(ingredient.getId());
                    ingredientDTO.setName(ingredient.getName());
                    ingredientDTO.setConcentration(fi.getConcentration());

                    List<AllergenDTO> allergenDTOs = ingredient.getIngredientAllergens().stream()
                            .map(ia -> {
                                AllergenDTO allergenDTO = new AllergenDTO();
                                allergenDTO.setId(ia.getAllergen().getId());
                                allergenDTO.setName(ia.getAllergen().getName());
                                allergenDTO.setConcentration(ia.getConcentration());
                                return allergenDTO;
                            })
                            .collect(Collectors.toList());

                    ingredientDTO.setAllergens(allergenDTOs);
                    return ingredientDTO;
                })
                .collect(Collectors.toList());

        dto.setIngredients(ingredients);
        return dto;
    }

    private Formula retrieveFormula(Long formulaId) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found"));
        return formula;
    }
}
