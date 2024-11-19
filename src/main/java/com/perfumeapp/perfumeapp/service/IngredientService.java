package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.exception.ResourceNotFoundException;
import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientAllergenRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
        Ingredient ingredient = retrieveIngredient(ingredientId);
        Allergen allergen = allergenRepository.findById(allergenDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Allergen not found"));

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setIngredient(ingredient);
        ingredientAllergen.setAllergen(allergen);
        ingredientAllergen.setConcentration(allergenDTO.getConcentration());

        ingredient.getIngredientAllergens().add(ingredientAllergen);
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }
    public IngredientDTO updateIngredientName(Long id, IngredientDTO ingredientDTO) {
        Ingredient ingredient = retrieveIngredient(id);
        ingredient.setName(ingredientDTO.getName());
        ingredientRepository.save(ingredient);
        return convertToDTO(ingredient);
    }



    public IngredientDTO updateAllergenConcentration(Long ingredientId, Long allergenId, double concentration) {
        Ingredient ingredient = retrieveIngredient(ingredientId);

        IngredientAllergen ingredientAllergen = ingredient.getIngredientAllergens().stream()
                .filter(ia -> ia.getAllergen().getId().equals(allergenId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Allergen not associated with the ingredient"));

        ingredientAllergen.setConcentration(concentration);
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }

    public IngredientDTO removeAllergen(Long ingredientId, Long allergenId) {
        Ingredient ingredient = retrieveIngredient(ingredientId);

        ingredient.getIngredientAllergens().removeIf(ia -> ia.getAllergen().getId().equals(allergenId));
        ingredientRepository.save(ingredient);

        return convertToDTO(ingredient);
    }

    private IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());


        List<AllergenDTO> allergens = (ingredient.getIngredientAllergens() == null)
            ? new ArrayList<>()
            : ingredient.getIngredientAllergens().stream()
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

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(Long id) {
        Ingredient i = retrieveIngredient(id);
        return convertToDTO(i);
    }

    public void removeIngredient(Long id) {
        Ingredient ingredient = retrieveIngredient(id);
        ingredientRepository.delete(ingredient);
    }

    private Ingredient retrieveIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        return ingredient;
    }
}
