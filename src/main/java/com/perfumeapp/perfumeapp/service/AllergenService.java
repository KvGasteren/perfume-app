package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Allergen;
import com.perfumeapp.perfumeapp.repository.AllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergenService {
    @Autowired
    private AllergenRepository allergenRepository;

    public Allergen createAllergen(Allergen allergen) {
        return allergenRepository.save(allergen);
    }

    public Allergen getAllergen(Long id) {
        return allergenRepository.findById(id).orElse(null);
    }

    public List<Allergen> getAllAllergens() {
        return allergenRepository.findAll();
    }

    public Allergen updateAllergen(Long id, Allergen updatedAllergen) {
        Allergen allergen = getAllergen(id);
        if (allergen != null) {
            allergen.setName(updatedAllergen.getName());
            return allergenRepository.save(allergen);
        }
        return null;
    }

    public void deleteAllergen(Long id) {
        allergenRepository.deleteById(id);
    }
}