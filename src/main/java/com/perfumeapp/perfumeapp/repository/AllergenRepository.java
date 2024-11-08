package com.perfumeapp.perfumeapp.repository;

import com.perfumeapp.perfumeapp.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {}
