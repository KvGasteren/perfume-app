package com.perfumeapp.perfumeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientAllergenDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    private IngredientDTO sampleIngredient;
    private IngredientAllergenDTO sampleAllergen;

    @BeforeEach
    void setUp() {
        // Sample Allergen
        sampleAllergen = new IngredientAllergenDTO();
        sampleAllergen.setAllergenId(1L);
        sampleAllergen.setAllergenName("Linalool");
        sampleAllergen.setAllergenMaxConcentration(0.4);

        // Sample Ingredient
        List<IngredientAllergenDTO> allergens = new ArrayList<>();
        allergens.add(sampleAllergen);

        sampleIngredient = new IngredientDTO();
        sampleIngredient.setId(1L);
        sampleIngredient.setName("Rose Oil");
        sampleIngredient.setAllergens(allergens);
    }

    @Test
    void testCreateIngredient() throws Exception {
        when(ingredientService.createIngredient(any(IngredientDTO.class))).thenReturn(sampleIngredient);

        mockMvc.perform(post("/api/ingredients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sampleIngredient))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Rose Oil"));

        verify(ingredientService, times(1)).createIngredient(any(IngredientDTO.class));
    }

    @Test
    void testAddAllergen() throws Exception {
        when(ingredientService.addAllergen(eq(1L), any(IngredientAllergenDTO.class))).thenReturn(sampleIngredient);

        mockMvc.perform(post("/api/ingredients/1/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAllergen)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.allergens[0].allergenName").value("Linalool"))
                .andExpect(jsonPath("$.allergens[0].maxConcentration").value(0.4));

        verify(ingredientService, times(1)).addAllergen(eq(1L), any(IngredientAllergenDTO.class));
    }

    @Test
    void testUpdateIngredientName() throws Exception {
        when(ingredientService.updateIngredientName(eq(1L), any(IngredientDTO.class))).thenReturn(sampleIngredient);

        mockMvc.perform(put("/api/ingredients/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sampleIngredient))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Rose Oil"));

        verify(ingredientService, times(1)).updateIngredientName(eq(1L), any(IngredientDTO.class));
    }

    @Test
    void testUpdateAllergenConcentration() throws Exception {
        sampleAllergen.setConcentration(0.02);
        sampleIngredient.setAllergens(List.of(sampleAllergen));
        when(ingredientService.updateAllergenConcentration(1L, 1L, 0.02)).thenReturn(sampleIngredient);

        mockMvc.perform(put("/api/ingredients/1/allergens/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(0.02))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.allergens[0].concentration").value(0.02));

        verify(ingredientService, times(1)).updateAllergenConcentration(1L, 1L, 0.02);
    }

    @Test
    void testRemoveAllergen() throws Exception {
        when(ingredientService.removeAllergen(1L, 1L)).thenReturn(sampleIngredient);

        mockMvc.perform(delete("/api/ingredients/1/allergens/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Rose Oil"));

        verify(ingredientService, times(1)).removeAllergen(1L, 1L);
    }

    @Test
    void testRemoveIngredient() throws Exception {
        doNothing().when(ingredientService).removeIngredient(1L);

        mockMvc.perform(delete("/api/ingredients/1")).andExpect(status().isNoContent());

        verify(ingredientService, times(1)).removeIngredient(1L);
    }

    @Test
    void testGetAllIngredients() throws Exception {
        when(ingredientService.getAllIngredients()).thenReturn(List.of(sampleIngredient));

        mockMvc.perform(get("/api/ingredients")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").value(1L)).andExpect(jsonPath("$[0].name").value("Rose Oil"));

        verify(ingredientService, times(1)).getAllIngredients();
    }

    @Test
    void testGetIngredientById() throws Exception {
        when(ingredientService.getIngredientById(1L)).thenReturn(sampleIngredient);

        mockMvc.perform(get("/api/ingredients/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Rose Oil"));

        verify(ingredientService, times(1)).getIngredientById(1L);
    }

}