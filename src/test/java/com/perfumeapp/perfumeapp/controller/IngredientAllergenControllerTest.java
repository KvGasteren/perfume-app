package com.perfumeapp.perfumeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfumeapp.perfumeapp.model.Ingredient;
import com.perfumeapp.perfumeapp.model.IngredientAllergen;
import com.perfumeapp.perfumeapp.service.IngredientAllergenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientAllergenController.class)
class IngredientAllergenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientAllergenService ingredientAllergenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addAllergenToIngredient_shouldReturnUpdatedIngredient() throws Exception {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Lavender Oil");

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setId(1L);
        ingredientAllergen.setConcentration(0.5);
        ingredient.getIngredientAllergens().add(ingredientAllergen);

        Mockito.when(ingredientAllergenService.addAllergenToIngredient(Mockito.eq(1L), Mockito.any(IngredientAllergen.class)))
                .thenReturn(ingredient);

        // Act and Assert
        mockMvc.perform(post("/api/ingredients/1/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientAllergen)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lavender Oil"))
                .andExpect(jsonPath("$.ingredientAllergens[0].concentration").value(0.5));
    }

    @Test
    void updateAllergenInIngredient_shouldReturnUpdatedIngredient() throws Exception {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Lavender Oil");

        IngredientAllergen ingredientAllergen = new IngredientAllergen();
        ingredientAllergen.setId(1L);
        ingredientAllergen.setConcentration(0.7); // Updated concentration
        ingredient.getIngredientAllergens().add(ingredientAllergen);

        Mockito.when(ingredientAllergenService.updateAllergenInIngredient(1L, 1L, 0.7))
                .thenReturn(ingredient);

        // Act and Assert
        mockMvc.perform(put("/api/ingredients/1/allergens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("0.7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lavender Oil"))
                .andExpect(jsonPath("$.ingredientAllergens[0].concentration").value(0.7));
    }

    @Test
    void removeAllergenFromIngredient_shouldReturnNoContent() throws Exception {
        // Arrange
        Mockito.when(ingredientAllergenService.removeAllergenFromIngredient(1L, 1L)).thenReturn(true);

        // Act and Assert
        mockMvc.perform(delete("/api/ingredients/1/allergens/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void removeAllergenFromIngredient_shouldReturnNotFound() throws Exception {
        // Arrange
        Mockito.when(ingredientAllergenService.removeAllergenFromIngredient(1L, 1L)).thenReturn(false);

        // Act and Assert
        mockMvc.perform(delete("/api/ingredients/1/allergens/1"))
                .andExpect(status().isNotFound());
    }
}
