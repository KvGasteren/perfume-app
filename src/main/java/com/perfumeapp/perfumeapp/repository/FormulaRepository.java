package com.perfumeapp.perfumeapp.repository;


import com.perfumeapp.perfumeapp.model.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {}