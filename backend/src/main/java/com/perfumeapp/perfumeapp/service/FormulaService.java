package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.*;
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

    private final FormulaRepository formulaRepository;

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

        formula.getFormulaIngredients().add(formulaIngredient);
        formulaRepository.save(formula);

        return convertToDTO(formula);
    }

    public FormulaDTO updateFormula(Long formulaId, FormulaDTO formulaDTO) {
        Formula formula = retrieveFormula(formulaId);
        formula.setName(formulaDTO.getName());
        formula.getFormulaIngredients().clear();

        for (FormulaIngredientDTO dto : formulaDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

            FormulaIngredient fi = new FormulaIngredient();
            fi.setFormula(formula);
            fi.setIngredient(ingredient);
            fi.setConcentration(dto.getConcentration());
            formula.getFormulaIngredients().add(fi);
        }
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

        List<FormulaIngredientDTO> ingredients = formula.getFormulaIngredients().stream()
                .map(fi -> {
                    Ingredient ingredient = fi.getIngredient();
                    FormulaIngredientDTO formulaIngredientDTO = new FormulaIngredientDTO();
                    formulaIngredientDTO.setId(ingredient.getId());
                    formulaIngredientDTO.setName(ingredient.getName());
                    formulaIngredientDTO.setParts(fi.getConcentration());

                    List<IngredientAllergenDTO> ingredientAllergenDTOS = ingredient.getIngredientAllergens().stream()
                            .map(ia -> {
                                IngredientAllergenDTO ingredientAllergenDTO = new IngredientAllergenDTO();
                                ingredientAllergenDTO.setId(ia.getAllergen().getId());
                                ingredientAllergenDTO.setName(ia.getAllergen().getName());
                                ingredientAllergenDTO.setConcentration(ia.getConcentration());
                                return ingredientAllergenDTO;
                            })
                            .collect(Collectors.toList());

                    formulaIngredientDTO.setAllergens(ingredientAllergenDTOS);
                    return formulaIngredientDTO;
                })
                .collect(Collectors.toList());

        // set the concentration of each ingredient (parts / total parts)

        double totalParts = ingredients.stream()
                .mapToDouble(FormulaIngredientDTO::getParts)
                .sum();
        for (FormulaIngredientDTO ingredientDTO : ingredients) {
            double concentration = totalParts > 0 ? ingredientDTO.getParts() / totalParts : 0.0;
            ingredientDTO.setConcentration(concentration);
        }

        dto.setIngredients(ingredients);
        return dto;
    }

    private Formula retrieveFormula(Long formulaId) {
        return formulaRepository.findById(formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found"));
    }
}
