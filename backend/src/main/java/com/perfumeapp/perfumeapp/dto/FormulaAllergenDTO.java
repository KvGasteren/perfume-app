package com.perfumeapp.perfumeapp.dto;


public class FormulaAllergenDTO {
    private Long allergenId;
    private String name;
    private double totalConcentrationInFormula;
    private double maxAllowedConcentration;
    private boolean exeedsLimit;

    public Long getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalConcentrationInFormula() {
        return totalConcentrationInFormula;
    }

    public void setTotalConcentrationInFormula(double totalConcentrationInFormula) {
        this.totalConcentrationInFormula = totalConcentrationInFormula;
    }

    public double getMaxAllowedConcentration() {
        return maxAllowedConcentration;
    }

    public void setMaxAllowedConcentration(double maxAllowedConcentration) {
        this.maxAllowedConcentration = maxAllowedConcentration;
    }

    public boolean isExeedsLimit() {
        return exeedsLimit;
    }

    public void setExeedsLimit(boolean exeedsLimit) {
        this.exeedsLimit = exeedsLimit;
    }
}
