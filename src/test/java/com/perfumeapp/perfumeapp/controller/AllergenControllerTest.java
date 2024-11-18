package com.perfumeapp.perfumeapp.controller;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.service.AllergenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AllergenController.class)
class AllergenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AllergenService allergenService;

    private AllergenDTO sampleAllergen;

    @BeforeEach
    void setUp() {
        sampleAllergen = new AllergenDTO();
        sampleAllergen.setId(1L);
        sampleAllergen.setName("Linalool");
        sampleAllergen.setMaxConcentration(0.1);
    }

    @Test
    void testGetAllAllergens() throws Exception {
        when(allergenService.getAllAllergens()).thenReturn(List.of(sampleAllergen));

        mockMvc.perform(get("/api/allergens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Linalool"))
                .andExpect(jsonPath("$[0].maxConcentration").value(0.1));

        verify(allergenService, times(1)).getAllAllergens();
    }

    @Test
    void testCreateAllergen() throws Exception {
        when(allergenService.createAllergen(Mockito.any(AllergenDTO.class))).thenReturn(sampleAllergen);

        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Linalool\",\"maxConcentration\":0.1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Linalool"))
                .andExpect(jsonPath("$.maxConcentration").value(0.1));

        verify(allergenService, times(1)).createAllergen(Mockito.any(AllergenDTO.class));
    }

    @Test
    void testGetAllergenById() throws Exception {
        when(allergenService.getAllergenById(1L)).thenReturn(sampleAllergen);

        mockMvc.perform(get("/api/allergens/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Linalool"))
                .andExpect(jsonPath("$.maxConcentration").value(0.1));

        verify(allergenService, times(1)).getAllergenById(1L);
    }
}
