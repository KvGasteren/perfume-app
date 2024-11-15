package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientAllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private AllergenRepository allergenRepository;

    public IngredientDTO createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return convertToDTO(savedIngredient);
    }

    public IngredientDTO addAllergen(Long ingredientId, AllergenDTO allergenDTO) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        Allergen allergen = allergenRepository.findById(allergenDTO.getId())
                .orElseThrow(() -> new RuntimeException("Allergen not found"));

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setIngredient(ingredient);
        ingredientAllergen.setAllergen(allergen);
        ingredientAllergen.setConcentration(allergenDTO.getConcentration());

        ingredient.getIngredientAllergens().add(ingredientAllergen);
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }

    public IngredientDTO updateAllergenConcentration(Long ingredientId, Long allergenId, double concentration) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        IngredientAllergen ingredientAllergen = ingredient.getIngredientAllergens().stream()
                .filter(ia -> ia.getAllergen().getId().equals(allergenId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Allergen not associated with the ingredient"));

        ingredientAllergen.setConcentration(concentration);
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }

    public IngredientDTO removeAllergen(Long ingredientId, Long allergenId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        ingredient.getIngredientAllergens().removeIf(ia -> ia.getAllergen().getId().equals(allergenId));
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }

    private IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());

        List<AllergenDTO> allergens = ingredient.getIngredientAllergens().stream()
                .map(ia -> {
                    AllergenDTO allergenDTO = new AllergenDTO();
                    allergenDTO.setId(ia.getAllergen().getId());
                    allergenDTO.setName(ia.getAllergen().getName());
                    allergenDTO.setConcentration(ia.getConcentration());
                    return allergenDTO;
                }).collect(Collectors.toList());

        dto.setAllergens(allergens);
        return dto;
    }
}
