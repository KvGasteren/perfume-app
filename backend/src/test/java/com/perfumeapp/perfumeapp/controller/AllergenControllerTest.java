package com.perfumeapp.perfumeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.exception.ResourceNotFoundException;
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

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void testCreateAllergen() throws Exception {
        when(allergenService.createAllergen(Mockito.any(AllergenDTO.class))).thenReturn(sampleAllergen);

        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAllergen)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Linalool"))
                .andExpect(jsonPath("$.maxConcentration").value(0.1));

        verify(allergenService, times(1)).createAllergen(Mockito.any(AllergenDTO.class));
    }

    @Test
    void testUpdateAllergen() throws Exception {
        // update sample allergen
        sampleAllergen.setMaxConcentration(0.2);
        sampleAllergen.setName("Updated Linalool");
        when(allergenService.updateAllergen(eq(1L), any(AllergenDTO.class))).thenReturn(sampleAllergen);

        mockMvc.perform(put("/api/allergens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAllergen)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Linalool")) // SampleAllergen has this name
                .andExpect(jsonPath("$.maxConcentration").value(0.2)); // SampleAllergen has this concentration

        verify(allergenService, times(1)).updateAllergen(eq(1L), argThat(dto ->
                dto.getName().equals("Updated Linalool") && dto.getMaxConcentration() == 0.2
        ));
    }

    @Test
    void testDeleteAllergen() throws Exception {
        doNothing().when(allergenService).deleteAllergen(1L);

        mockMvc.perform(delete("/api/allergens/1"))
                .andExpect(status().isNoContent());

        verify(allergenService, times(1)).deleteAllergen(1L);
    }

    @Test
    void testGetAllergenById_NotFound() throws Exception {
        when(allergenService.getAllergenById(99L)).thenThrow(new ResourceNotFoundException("Allergen not found"));

        mockMvc.perform(get("/api/allergens/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAllergen_BadRequest() throws Exception {
        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Invalid payload
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAllergen_NotFound() throws Exception {
        when(allergenService.updateAllergen(eq(99L), any(AllergenDTO.class)))
                .thenThrow(new ResourceNotFoundException("Allergen not found"));

        mockMvc.perform(put("/api/allergens/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAllergen)))
                .andExpect(status().isNotFound());
    }

}
