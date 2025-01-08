package com.perfumeapp.perfumeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfumeapp.perfumeapp.dto.FormulaDTO;
import com.perfumeapp.perfumeapp.dto.IngredientDTO;
import com.perfumeapp.perfumeapp.service.FormulaService;
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

@WebMvcTest(FormulaController.class)
class FormulaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormulaService formulaService;

    @Autowired
    private ObjectMapper objectMapper;

    private FormulaDTO sampleFormula;
    private IngredientDTO sampleIngredient;

    @BeforeEach
    void setUp() {
        sampleFormula = new FormulaDTO();
        sampleFormula.setId(1L);
        sampleFormula.setName("Test Formula");

        sampleIngredient = new IngredientDTO();
        sampleIngredient.setId(1L);
        sampleIngredient.setName("Rose Oil");
        sampleIngredient.setConcentration(0.5);

        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(sampleIngredient);
        sampleFormula.setIngredients(ingredients);
    }

    @Test
    void testCreateFormula() throws Exception {
        when(formulaService.createFormula(any(FormulaDTO.class))).thenReturn(sampleFormula);

        mockMvc.perform(post("/api/formulas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFormula)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Formula"));

        verify(formulaService, times(1)).createFormula(any(FormulaDTO.class));
    }

    @Test
    void testAddIngredient() throws Exception {
        when(formulaService.addIngredient(eq(1L), any(IngredientDTO.class))).thenReturn(sampleFormula);

        mockMvc.perform(post("/api/formulas/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleIngredient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.ingredients[0].name").value("Rose Oil"));

        verify(formulaService, times(1)).addIngredient(eq(1L), any(IngredientDTO.class));
    }

    @Test
    void testUpdateFormula() throws Exception {
        when(formulaService.updateFormulaName(eq(1L), any(FormulaDTO.class))).thenReturn(sampleFormula);

        mockMvc.perform(put("/api/formulas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFormula)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Formula"));

        verify(formulaService, times(1)).updateFormulaName(eq(1L), any(FormulaDTO.class));
    }

    @Test
    void testUpdateIngredientConcentration() throws Exception {
        when(formulaService.updateIngredientConcentration(eq(1L), eq(1L), eq(0.7))).thenReturn(sampleFormula);

        mockMvc.perform(put("/api/formulas/1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(0.7)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));

        verify(formulaService, times(1)).updateIngredientConcentration(eq(1L), eq(1L), eq(0.7));
    }

    @Test
    void testDeleteFormula() throws Exception {
        doNothing().when(formulaService).removeFormula(eq(1L));

        mockMvc.perform(delete("/api/formulas/1"))
                .andExpect(status().isNoContent());

        verify(formulaService, times(1)).removeFormula(eq(1L));
    }

    @Test
    void testRemoveIngredient() throws Exception {
        when(formulaService.removeIngredient(eq(1L), eq(1L))).thenReturn(sampleFormula);

        mockMvc.perform(delete("/api/formulas/1/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));

        verify(formulaService, times(1)).removeIngredient(eq(1L), eq(1L));
    }

    @Test
    void testGetAllFormulas() throws Exception {
        when(formulaService.getAllFormulas()).thenReturn(List.of(sampleFormula));

        mockMvc.perform(get("/api/formulas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Formula"));

        verify(formulaService, times(1)).getAllFormulas();
    }

    @Test
    void testGetFormulaById() throws Exception {
        when(formulaService.getFormulaById(eq(1L))).thenReturn(sampleFormula);

        mockMvc.perform(get("/api/formulas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Formula"));

        verify(formulaService, times(1)).getFormulaById(eq(1L));
    }
}
