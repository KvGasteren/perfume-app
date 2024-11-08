package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.*;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import com.perfumeapp.perfumeapp.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FormulaServiceTest {

    @Autowired
    private FormulaService formulaService;

    @MockBean
    private FormulaRepository formulaRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Test
    public void testCalculateAllergenComposition() {
        // Step 1: Set up sample allergens, ingredients, and a formula

        // Create allergens
        Allergen allergen1 = new Allergen();
        allergen1.setId(1L);
        allergen1.setName("AllergenA");

        Allergen allergen2 = new Allergen();
        allergen2.setId(2L);
        allergen2.setName("AllergenB");

        // Create ingredients with allergens
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient1");

        IngredientAllergen ia1 = new IngredientAllergen();
        ia1.setAllergen(allergen1);
        ia1.setConcentration(0.2);  // 20% concentration of AllergenA

        IngredientAllergen ia2 = new IngredientAllergen();
        ia2.setAllergen(allergen2);
        ia2.setConcentration(0.1);  // 10% concentration of AllergenB

        ingredient1.setIngredientAllergens(Arrays.asList(ia1, ia2));

        // Create formula and add ingredient with amount
        Formula formula = new Formula();
        FormulaIngredient formulaIngredient1 = new FormulaIngredient();
        formulaIngredient1.setIngredient(ingredient1);
        formulaIngredient1.setAmount(50); // Let's say 50 units of ingredient1

        formula.setFormulaIngredients(Arrays.asList(formulaIngredient1));

        // Step 2: Mock repository calls
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));

        // Step 3: Perform the calculation
        Map<String, Double> allergenComposition = formulaService.calculateAllergenComposition(formula);

        // Step 4: Verify the results
        assertEquals(0.2, allergenComposition.get("AllergenA"), 0.01);  // Expect around 0.2 (20%)
        assertEquals(0.1, allergenComposition.get("AllergenB"), 0.01);  // Expect around 0.1 (10%)
    }
}
