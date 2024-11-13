package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergenService {
    @Autowired
    private AllergenRepository allergenRepository;

    public Allergen createAllergen(Allergen allergen) {
        return allergenRepository.save(allergen);
    }

    public List<Allergen> getAllAllergens() {
        return allergenRepository.findAll();
    }

    public Optional<Allergen> getAllergenById(Long id) {
        return allergenRepository.findById(id);
    }

    public Allergen updateAllergen(Long id, Allergen allergenDetails) {
        Allergen allergen = allergenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Allergen not found"));
//
        // Update fields if present in allergenDetails
        if (allergenDetails.getName() != null) {
            allergen.setName(allergenDetails.getName());
        }
        if (allergenDetails.getMaxConcentration() != null) {
            allergen.setMaxConcentration(allergenDetails.getMaxConcentration());
        }

        // Save and return the updated allergen
        return allergenRepository.save(allergen);

    }

    public void deleteAllergen(Long id) {
        allergenRepository.deleteById(id);
    }
}
