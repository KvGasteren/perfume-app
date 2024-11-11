package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientAllergenServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private AllergenRepository allergenRepository;

    @InjectMocks
    private IngredientAllergenService ingredientAllergenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAllergenToIngredient_shouldAddAllergenWithConcentration() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Rose Oil");

        Allergen allergen = new Allergen();
        allergen.setId(1L);
        allergen.setName("Linalool");

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setAllergen(allergen);
        ingredientAllergen.setConcentration(0.5);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(allergenRepository.findById(1L)).thenReturn(Optional.of(allergen));

        // Act
        Ingredient result = ingredientAllergenService.addAllergenToIngredient(1L, ingredientAllergen);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIngredientAllergens().size());
        assertEquals("Linalool", result.getIngredientAllergens().get(0).getAllergen().getName());
        assertEquals(0.5, result.getIngredientAllergens().get(0).getConcentration());
    }

    @Test
    void updateAllergenInIngredient_shouldUpdateConcentration() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Allergen allergen = new Allergen();
        allergen.setId(1L);

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setAllergen(allergen);
        ingredientAllergen.setConcentration(0.2);
        ingredient.getIngredientAllergens().add(ingredientAllergen);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        // Act
        Ingredient result = ingredientAllergenService.updateAllergenInIngredient(1L, 1L, 0.6);

        // Assert
        assertNotNull(result);
        assertEquals(0.6, result.getIngredientAllergens().get(0).getConcentration());
    }

    @Test
    void removeAllergenFromIngredient_shouldRemoveAllergen() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Allergen allergen = new Allergen();
        allergen.setId(1L);

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setAllergen(allergen);
        ingredient.getIngredientAllergens().add(ingredientAllergen);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        // Act
        boolean removed = ingredientAllergenService.removeAllergenFromIngredient(1L, 1L);

        // Assert
        assertTrue(removed);
        assertTrue(ingredient.getIngredientAllergens().isEmpty());
    }
}
