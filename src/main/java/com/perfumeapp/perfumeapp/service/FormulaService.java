package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.FormulaDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
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
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public FormulaDTO createFormula(FormulaDTO formulaDTO) {
        Formula formula = new Formula();
        formula.setName(formulaDTO.getName());
        Formula savedFormula = formulaRepository.save(formula);
        return convertToDTO(savedFormula);
    }

    public FormulaDTO addIngredient(Long formulaId, IngredientDTO ingredientDTO) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        Ingredient ingredient = ingredientRepository.findById(ingredientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaIngredient.setFormula(formula);
        formulaIngredient.setIngredient(ingredient);
        formulaIngredient.setConcentration(ingredientDTO.getConcentration());

        formula.getFormulaIngredients().add(formulaIngredient);
        formulaRepository.save(formula);

        return convertToDTO(formula);
    }

    public FormulaDTO updateFormulaName(Long formulaId, FormulaDTO formulaDTO) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        formula.setName(formulaDTO.getName());
        formulaRepository.save(formula);
        return convertToDTO(formula);
    }

    public FormulaDTO updateIngredientConcentration(Long formulaId, Long ingredientId, double concentration) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));

        FormulaIngredient formulaIngredient = formula.getFormulaIngredients().stream()
                .filter(fi -> fi.getIngredient().getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not associated with the formula"));

        formulaIngredient.setConcentration(concentration);
        formulaRepository.save(formula);

        return convertToDTO(formula);
    }

    public FormulaDTO removeIngredient(Long formulaId, Long ingredientId) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        formula.getFormulaIngredients().removeIf(fi -> fi.getIngredient().getId().equals(ingredientId));
        formulaRepository.save(formula);
        return convertToDTO(formula);
    }

    public void removeFormula(Long id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        formulaRepository.delete(formula);
    }

    public FormulaDTO getFormulaById(Long id) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
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
}
