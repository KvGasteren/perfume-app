package com.perfumeapp.perfumeapp.service;

import com.perfumeapp.perfumeapp.model.Formula;
import com.perfumeapp.perfumeapp.repository.FormulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

    public Formula createFormula(Formula formula) {
        return formulaRepository.save(formula);
    }

    public List<Formula> getAllFormulas() {
        return formulaRepository.findAll();
    }

    public Optional<Formula> getFormulaById(Long id) {
        return formulaRepository.findById(id);
    }

    public Formula updateFormula(Long id, Formula formulaDetails) {
        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formula not found"));
        if (formulaDetails.getName() != null) {
            formula.setName(formulaDetails.getName());
        }
        return formulaRepository.save(formula);
    }

    public void deleteFormula(Long id) {
        formulaRepository.deleteById(id);
    }
}
