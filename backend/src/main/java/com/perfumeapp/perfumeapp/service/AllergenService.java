package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.dto.AllergenDTO;
import com.perfumeapp.perfumeapp.exception.ResourceNotFoundException;
import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllergenService {

    private final AllergenRepository allergenRepository;
    public AllergenService(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    public AllergenDTO createAllergen(AllergenDTO allergenDTO) {
        Allergen allergen = new Allergen();
        allergen.setId(allergenDTO.getId());
        allergen.setName(allergenDTO.getName());
        allergen.setMaxConcentration(allergenDTO.getMaxConcentration());

        allergenRepository.save(allergen);
        return convertToDTO(allergen);
    }

    public List<AllergenDTO> getAllAllergens() {
        return allergenRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AllergenDTO getAllergenById(Long id) {
        return convertToDTO(retreiveById(id));
    }

    public AllergenDTO updateAllergen(Long id, AllergenDTO allergenDetails) {
        Allergen allergen = retreiveById(id);
        // Update fields if present in allergenDetails
        if (allergenDetails.getName() != null) {
            allergen.setName(allergenDetails.getName());
        }
        allergen.setMaxConcentration(allergenDetails.getMaxConcentration());
        allergenRepository.save(allergen);
        return convertToDTO(allergen);
    }

    public void deleteAllergen(Long id) {
        allergenRepository.deleteById(id);
    }

    private AllergenDTO convertToDTO(Allergen allergen) {
        AllergenDTO dto = new AllergenDTO();
        dto.setId(allergen.getId());
        dto.setName(allergen.getName());
        dto.setMaxConcentration(allergen.getMaxConcentration());
        return dto;
    }

    private Allergen retreiveById(Long id) {
        return allergenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen not found"));
    }
}
