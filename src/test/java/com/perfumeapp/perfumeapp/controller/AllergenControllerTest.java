package com.perfumeapp.perfumeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.service.AllergenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AllergenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON

    @MockBean
    private AllergenService allergenService;

    @Test
    public void testCreateAllergen() throws Exception {
        // Arrange
        Allergen allergen = new Allergen();
        allergen.setId(1L);
        allergen.setName("TestAllergen");

        when(allergenService.createAllergen(any(Allergen.class))).thenReturn(allergen);

        // Act and Assert
        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(allergen)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("TestAllergen"));
    }

    @Test
    public void testGetAllergen() throws Exception {
        Allergen allergen = new Allergen();
        allergen.setId(1L);
        allergen.setName("TestAllergen");

        when(allergenService.getAllergen(1L)).thenReturn(allergen);

        mockMvc.perform(get("/api/allergens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestAllergen"));
    }
}
